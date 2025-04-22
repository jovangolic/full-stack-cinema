import React, { useEffect, useState } from 'react';
import { getAllTickets } from '../utils/AppFunction';

const AllTickets = () => {
    const [tickets, setTickets] = useState([]);
    const [error, setError] = useState(null);

    /*useEffect(() => {
        const fetchTickets = async () => {
            try {
                const response = await api.get('/tickets/all-tickets', {
                    headers: getHeader()
                });
                setTickets(response.data);
            } catch (err) {
                console.error("Greška pri dobavljanju karata:", err);
                setError("Neuspešno učitavanje karata.");
            }
        };

        fetchTickets();
    }, []);*/
    useEffect(() => {
        const fetchTickets = async () => {
            try {
                const data = await getAllTickets(); // poziv funkcije iz AppFunction
                setTickets(data);
            } catch (err) {
                console.error("Greška pri dobavljanju karata:", err);
                setError("Neuspešno učitavanje karata.");
            }
        };

        fetchTickets();
    }, []);
    
    const parseDateArray = (arr) => {
        return new Date(arr[0], arr[1] - 1, arr[2], arr[3], arr[4], arr[5], arr[6] / 1000000);
      };

    return (
        <div className="container mt-4">
            <h2>All Tickets (Admin)</h2>
            {error && <p className="text-danger">{error}</p>}
            <table className="table table-bordered">
                <thead>
                    <tr>
                    <th>ID</th>
                    <th>Movie</th>
                    <th>Hall</th>
                    <th>Seat</th>
                    <th>Status</th>
                    <th>Sale_date_time</th>
                    <th>Buyer</th>
                    </tr>
                </thead>
                <tbody>
                    {tickets.map((ticket) => (
                    <tr key={ticket.id}>
                        <td>{ticket.id}</td>
                        <td>{ticket.projectionTicketResponse?.movieResponse?.name || '/'}</td>
                        <td>{ticket.projectionTicketResponse?.hallResponse?.name || '/'}</td>
                        <td>
                        {ticket.seatResponse
                            ? `Red ${ticket.seatResponse.id} / Sedište ${ticket.seatResponse.seatNumber}`
                            : '/'}
                        </td>
                        <td>{ticket.status}</td>
                        <td>{parseDateArray(ticket.saleDateTime).toLocaleString()}</td>
                        <td>{ticket.userDto?.email || 'Unknown'}</td>
                    </tr>
                    ))}
                </tbody>
                </table>
        </div>
    );
};

export default AllTickets;