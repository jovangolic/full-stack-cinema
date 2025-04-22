import React, { useEffect, useState, useContext } from 'react';
import { Form, Button, Container, Row, Col, Card } from 'react-bootstrap';
import { getAllProjections, getSeatsByProjectionId, buyTicket, getProjectionById } from '../utils/AppFunction';
import { AuthContext } from "../auth/AuthProvider";
import {api} from "../utils/AppFunction";



function BuyTicketForm() {
    const { user } = useContext(AuthContext); // preuzimanje korisnika iz AuthProvider-a
  
    const [projections, setProjections] = useState([]);
    const [seats, setSeats] = useState([]);
    const [ticket, setTicket] = useState({
      projection: '',
      seatNumber: '',
      status: '',
      user: user,
      email: '',
      quantity: 1,
      price: 0,
    });
    const [selectedProjection, setSelectedProjection] = useState(null);
    const [totalPrice, setTotalPrice] = useState(0);
  
    // Definišemo statuse karata ručno
    const ticketStatuses = ['RESERVED', 'PURCHASED',"CANCELED"];
  
    useEffect(() => {
      getAllProjections().then(res => {
        setProjections(res.data);
      });
    }, []);
  
    useEffect(() => {
      if (ticket.projection) {
        getSeatsByProjectionId(ticket.projection).then(res => {
          setSeats(res.data);
        });
  
        const proj = projections.find(p => p.id === parseInt(ticket.projection));
        setSelectedProjection(proj);
  
        if (proj && proj.price) {
          setTotalPrice(ticket.quantity * proj.price);
          setTicket(prev => ({ ...prev, price: proj.price }));
        }
      }
    }, [ticket.projection,projections]);
  
    useEffect(() => {
      if (ticket.price) {
        setTotalPrice(ticket.quantity * ticket.price);
      }
    }, [ticket.quantity, ticket.price]);
  
    const handleChange = e => {
      const { name, value } = e.target;
      setTicket(prev => ({
        ...prev,
        [name]: name === 'quantity' ? parseInt(value) || 1 : value
      }));
    };

    /*useEffect(() => {
      getAllProjections().then(res => {
        if (Array.isArray(res.data)) {
          setProjections(res.data);
        } else {
          console.error('Greška: Projekcije nisu niz', res.data);
          setProjections([]); // fallback
        }
      }).catch(err => {
        console.error('Greška pri dobavljanju projekcija:', err);
        setProjections([]);
      });
    }, []);*/
    useEffect(() => {
      api.get("/projections/all-projections")
        .then(res => {
          console.log(res.data);
          setProjections(res.data);
        })
        .catch(err => {
          console.error("Greška pri učitavanju projekcija:", err);
        });
    }, []);
  
    const handleSubmit = e => {
      e.preventDefault();
      buyTicket(ticket)
        .then(() => {
          alert('Uspešno kupljena karta!');
          window.location.reload();
        })
        .catch(err => {
          console.error('Greška prilikom kupovine:', err);
          alert('Došlo je do greške.');
        });
    };

    const handleProjectionChange = async (e) => {
      const selectedId = e.target.value;
      setTicket(prev => ({ ...prev, projection: selectedId }));
    
      try {
        const projectionRes = await api.get(`/projections/projection/${selectedId}`);
        console.log(projectionRes.data); // Dodaj log za projekciju
        setSelectedProjection(projectionRes.data);
    
        const seatsRes = await api.get(`/seats/by-projection/${selectedId}`);
        console.log(seatsRes.data); // Dodaj log za sedišta
        setSeats(seatsRes.data);
      } catch (error) {
        console.error("Greška pri učitavanju projekcije ili sedišta:", error);
      }
    };
  
    return (
      <Container className="mt-4">
        <Row>
          <Col md={6}>
            <Card className="mb-3">
            {selectedProjection && selectedProjection.movieProjectionResponse && (
              <>
                <Card.Img
                  variant="top"
                  src={`data:image/jpeg;base64,${selectedProjection.movieImage}`}
                  alt="Poster"
                />
                <Card.Body>
                  <Card.Title>{selectedProjection.movieProjectionResponse.name}</Card.Title>
                </Card.Body>
              </>
            )}
            </Card>
  
            <h4>Ukupna cena: <strong>{totalPrice} RSD</strong></h4>
          </Col>
  
          <Col md={6}>
            <h3>Kupovina karte</h3>
            <Form onSubmit={handleSubmit}>
              <Form.Group controlId="projection" className="mb-3">
                <Form.Label>Projekcija</Form.Label>
                <Form.Select
                  name="projection"
                  value={ticket.projection}
                  onChange={handleProjectionChange}
                  required
                >
                  <option value="">Izaberi projekciju</option>
                  {Array.isArray(projections) && projections.map(proj => (
                    <option key={proj.id} value={proj.id}>
                      {proj.movieProjectionResponse.name} - {proj.dateTime}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
  
              <Form.Group controlId="seatNumber" className="mb-3">
                <Form.Label>Sedište</Form.Label>
                <Form.Select
                  name="seatNumber"
                  value={ticket.seatNumber}
                  onChange={handleChange}
                  required
                >
                  <option value="">Izaberi sedište</option>
                  {Array.isArray(seats) && seats.map(seat => (
                  <option key={seat.id} value={seat.id}>
                    Red {seat.rowNumber}, Broj {seat.seatNumber}
                  </option>
                ))}
                </Form.Select>
              </Form.Group>
  
              <Form.Group controlId="status" className="mb-3">
                <Form.Label>Status karte</Form.Label>
                <Form.Select
                  name="status"
                  value={ticket.status}
                  onChange={handleChange}
                  required
                >
                  <option value="">Izaberi status</option>
                  {ticketStatuses.map(status => (
                    <option key={status} value={status}>
                      {status}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
  
              <Form.Group controlId="email" className="mb-3">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  type="email"
                  name="email"
                  value={ticket.email}
                  onChange={handleChange}
                  required
                />
              </Form.Group>
  
              <Form.Group controlId="quantity" className="mb-3">
                <Form.Label>Količina</Form.Label>
                <Form.Control
                  type="number"
                  name="quantity"
                  min="1"
                  value={ticket.quantity}
                  onChange={handleChange}
                />
              </Form.Group>
  
              <Button variant="primary" type="submit">
                Kupi kartu
              </Button>
            </Form>
          </Col>
        </Row>
      </Container>
    );
  }
  
  export default BuyTicketForm;