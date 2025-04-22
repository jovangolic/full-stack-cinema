import React, { useState, useEffect, useContext } from 'react';
import { Form, Button, Card, Container, Col } from 'react-bootstrap';
import { getAllProjections, buyTicket, getSeatsByProjectionId,getSeats,getAllHalls } from '../utils/AppFunction';
import { AuthContext, useAuth } from "../auth/AuthProvider";
import BoughtTicketsSummary from './BoughtTicketsSummary';
import { useNavigate, useParams } from 'react-router-dom';
import Dropdown from 'react-bootstrap/Dropdown';


const BuyTicketForm = () => {
  
  //const { user: currentUser } = useContext(AuthContext);
  const { user: currentUser } = useAuth();
  const [projections, setProjections] = useState([]);
  const [seatNumber, setSeatNumber] = useState('');
  const [selectedProjectionId, setSelectedProjectionId] = useState('');
  const [selectedProjection, setSelectedProjection] = useState(""); 
  const [seatId, setSeatId] = useState(''); 
  const [seats, setSeats] = useState([]);
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [validated, setValidated] = useState(false)
  const [ticket, setTicket] = useState({
        projection: '',
        seatNumber: seatNumber,
        status: '',
        user: currentUser,
        email: '',
        quantity: 1,
        price: 0,
      });

  const ticketStatuses = ["RESERVED", "PURCHASED", "AVAILABLE", "CANCELLED"];
  const { projectionId} = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProjections = async() => {
      try{
        const data = await getAllProjections();
        console.log("projections: ",data);
        setProjections(data);
      }
      catch(error){
        console.error("Error while fetching projections: ", error);
      }
    };
    fetchProjections();
  },[]);

  useEffect(() => {
    const fetchHalls = async() =>{
      try{
        const response = await getAllHalls();
        return response.data;
      }
      catch(error){
        console.error("Error while fetching halls",error);
      }
    }
    fetchHalls();
  },[])

  useEffect(() => {
    const fetchSeats = async () => {
      if (!selectedProjectionId) return;

      try {
        const data = await getSeatsByProjectionId(selectedProjectionId);
        console.log("Seats:", data);
        setSeats(data);
      } catch (error) {
        console.error('Error while fetching seats:', error);
      }
    };

    fetchSeats();
  }, [selectedProjectionId]);

  useEffect(() => {
    if (!ticket.status) {
      setTicket((prev) => ({ ...prev, status: '' }));
    }
  }, []);

  const handleProjectionChange = async (e) => {
    const projectionId = e.target.value;
    setSelectedProjectionId(projectionId);
    const selected = projections.find((p) => p.id.toString() === projectionId);
    setSelectedProjection(selected); 
    try {
      const seatData = await getSeatsByProjectionId(projectionId);
      setSeats(seatData);
    } catch (error) {
      console.error("Greška pri dohvatanju sedišta:", error);
    }
  };

  const handleSeatChange = (e) => {
    const seatIdValue = e.target.value;
    setSeatId(seatIdValue);
  
    const seat = seats.find((s) => s.id.toString() === seatIdValue);
    if (seat) {
      setSeatNumber(seat.seatNumber);
    }
  };

  const formatDateTime = (dateArray) => {
    if (!Array.isArray(dateArray) || dateArray.length < 5) return '';
    const [year, month, day, hour, minute] = dateArray;
    return `${day.toString().padStart(2, '0')}.${month.toString().padStart(2, '0')}.${year}. ${hour}:${minute.toString().padStart(2, '0')}`;
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    console.log(`Promena u formi: ${name} = ${value}`); 
    setTicket((prevTicket) => ({
      ...prevTicket,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!ticket.status) {
      alert('Izaberite status!');
      return;
    }
    if (!selectedProjectionId || !seatId) {
      alert('Izaberite projekciju i sedište!');
      return;
    }
    else{
      setIsSubmitted(true);
    }
    
    setValidated(true)
  };

  const handleConfirm = async () => {
    /*if (!currentUser || !currentUser.id || !currentUser.email) {
      alert('You are not logged in.');
      return;
    }*/
    const token = localStorage.getItem("token"); 

    const headers = {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    };
  
    const ticketRequest = {
      projectionId: selectedProjectionId,
      seatNumber: seatNumber,
      price: selectedProjection.ticketPrice,
      userId: currentUser.id,
      email: currentUser.email,
      quantity: ticket.quantity || 1
    };
    console.log("Ticket request:", ticketRequest);
    console.log("headers: ", headers);
  
    try {
      const confirmation = await buyTicket(
        projectionId,
        ticketRequest,
        ticket.status
      );
  
      const code = confirmation.split(":").pop().trim();
      setIsSubmitted(true);
      navigate("/bought-ticket-success", {
        state: { message: `Vaš kod potvrde je: ${code}` }
      });
  
    } catch (error) {
      console.error("Greška pri kupovini:", error);
      navigate("/bought-ticket-success", {
        state: { error: error.response?.data || error.message }
      });
    }
    console.log("seatId:", seatId);
    console.log("parsed seatNumber:", parseInt(seatId, 10));
    console.log("selectedProjection:", selectedProjection);
  };


  /*if (isSubmitted) {
    return (
      <Container className="mt-5">
        <Card>
          <Card.Header>Ticket Summary</Card.Header>
          <Card.Body>
            <p>Movie: {selectedProjection.movieProjectionResponse.name}</p>
            <p>Hall: {selectedProjection.projectionHallResponse.name}</p>
            <p>Seat: {seatNumber}</p>
            <p>Status: {ticket.status}</p>
            <Button variant="success" onClick={handleConfirm}>
               Confirm Purchase
            </Button>
          </Card.Body>
        </Card>
      </Container>
    )
  }*/


    return (
      <Container className="mt-5">
        <Form onSubmit={handleSubmit}>
          <Form.Group controlId="projectionSelect" className="mb-3">
            <Form.Label>Select a projection</Form.Label>
            <Form.Select value={selectedProjectionId} onChange={handleProjectionChange}>
              <option value="">-- Select projection --</option>
              {projections.map((proj) => (
                <option key={proj.id} value={proj.id}>
                  {proj.movieProjectionResponse.name} - {proj.dateTime}
                </option>
              ))}
            </Form.Select>
          </Form.Group>
  
          {selectedProjection && (
            <Card className="mt-3" style={{ width: '18rem' }}>
              <Card.Img
                variant="top"
                src={`data:image/jpeg;base64,${selectedProjection.movieProjectionResponse.photo}`}
                alt="Poster"
              />
              <Card.Body>
                <Card.Title>{selectedProjection.movieProjectionResponse.name}</Card.Title>
                <Card.Text>
                  Hall: {selectedProjection.projectionHallResponse.name} <br />
                  Price: {selectedProjection.ticketPrice} RSD <br />
                  Date & Time: {selectedProjection.dateTime}
                </Card.Text>
              </Card.Body>
            </Card>
          )}
  
          <Form.Group controlId="seatSelect" className="mb-3">
            <Form.Label>Choose your seat</Form.Label>
            <Form.Control
              as="select"
              value={seatId}
              onChange={handleSeatChange}
              disabled={!seats.length}
            >
              <option value="">-- Choose seat --</option>
              {seats.map((seat) => (
                <option key={seat.id} value={seat.id}>
                  Seat {seat.seatNumber}
                </option>
              ))}
            </Form.Control>
          </Form.Group>
  
          <Form.Group controlId="status" className="mb-3">
            <Form.Label>Ticket Status</Form.Label>
            <Form.Select
              name="status"
              value={ticket.status}
              onChange={handleChange}
              required
            >
              <option value="">Choose status</option>
              {ticketStatuses.map((status) => (
                <option key={status} value={status}>
                  {status}
                </option>
              ))}
            </Form.Select>
          </Form.Group>
  
          <Button variant="success" onClick={handleConfirm}>
            Confirm Purchase
          </Button>
        </Form>
  
        <Col md={5}>
        <BoughtTicketsSummary
          buying={{
            projection: selectedProjection?.movieName,
            seatNumber: seatNumber,
            saleDateTime: new Date().toISOString(), // ili ako već imaš vreme prodaje
            price: selectedProjection?.ticketPrice,
            status: ticket.status,
            email: currentUser?.email
          }}
          payment={selectedProjection?.ticketPrice}
          isFormValid={validated}
          onConfirm={handleConfirm}
/>
    </Col>
      </Container>
    );
};

export default BuyTicketForm;