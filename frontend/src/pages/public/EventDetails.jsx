import React, { useState, useEffect, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import API from '../../api/axios';
import { AuthContext } from '../../context/AuthContext';
import { SeatMap } from '../../components/SeatMap';
import { MapPin, Calendar, User, ShieldAlert } from 'lucide-react';

export const EventDetails = () => {
  const { id } = useParams();
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();

  const [event, setEvent] = useState(null);
  const [seats, setSeats] = useState([]);
  const [selectedSeats, setSelectedSeats] = useState([]);
  const [loading, setLoading] = useState(true);
  const [reserveLoading, setReserveLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');

  useEffect(() => {
    fetchEventAndSeats();
  }, [id]);

  const fetchEventAndSeats = async () => {
    try {
      const [eventRes, seatsRes] = await Promise.all([
        API.get(`/events/${id}`),
        API.get(`/events/${id}/seats`)
      ]);

      if (eventRes.data.success) {
        setEvent(eventRes.data.data);
      }
      if (seatsRes.data.success) {
        setSeats(seatsRes.data.data || []);
      }
    } catch (err) {
      setErrorMsg('Failed to load event details.');
    } finally {
      setLoading(false);
    }
  };

  const handleToggleSeat = (seat) => {
    if (selectedSeats.some(s => s.id === seat.id)) {
      setSelectedSeats(selectedSeats.filter(s => s.id !== seat.id));
    } else {
      setSelectedSeats([...selectedSeats, seat]);
    }
  };

  const handleReserveSeats = async () => {
    if (!user) {
      navigate('/login');
      return;
    }

    setReserveLoading(true);
    setErrorMsg('');
    try {
      const seatIds = selectedSeats.map(s => s.id);
      const res = await API.post('/reservations', {
        eventId: parseInt(id),
        seatIds
      });

      if (res.data.success) {
        const reservation = res.data.data;
        navigate(`/checkout/${reservation.id}`);
      }
    } catch (err) {
      setErrorMsg(err.response?.data?.message || 'Selected seat is no longer available. Please select another seat.');
      fetchEventAndSeats(); // Refresh availability state
    } finally {
      setReserveLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="text-center py-5">
        <div className="spinner-border text-primary" role="status"></div>
      </div>
    );
  }

  if (!event) {
    return (
      <div className="container py-5 text-center">
        <h4>Event Not Found</h4>
      </div>
    );
  }

  return (
    <div className="container py-5">
      {errorMsg && (
        <div className="alert alert-danger d-flex align-items-center gap-2 mb-4">
          <ShieldAlert size={20} /> {errorMsg}
        </div>
      )}

      {/* Event Header Banner */}
      <div className="card shadow-sm border-0 rounded-4 overflow-hidden mb-5">
        <div className="row g-0">
          <div className="col-md-5">
            <img
              src={event.posterUrl || 'https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&w=800&q=80'}
              alt={event.title}
              className="w-100 h-100"
              style={{ objectFit: 'cover', minHeight: '300px' }}
            />
          </div>
          <div className="col-md-7 p-4 p-lg-5 d-flex flex-column justify-content-center">
            <span className="badge bg-primary text-white w-fit mb-3">{event.category}</span>
            <h2 className="display-6 fw-bold text-dark mb-3">{event.title}</h2>
            <p className="text-muted mb-4">{event.description}</p>

            <div className="d-flex flex-wrap gap-4 small text-muted border-top pt-3">
              <div>
                <MapPin className="text-primary me-1 inline" size={18} />
                <span className="fw-semibold text-dark">{event.venueName}</span>, {event.venueCity}
              </div>
              <div>
                <Calendar className="text-primary me-1 inline" size={18} />
                <span className="fw-semibold text-dark">{new Date(event.startTime).toLocaleString()}</span>
              </div>
              <div>
                <User className="text-primary me-1 inline" size={18} />
                Organizer: <span className="fw-semibold text-dark">{event.organizerName}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Interactive Seating Layout Chart */}
      <h4 className="fw-bold mb-3">Select Your Seats</h4>
      <SeatMap
        seats={seats}
        selectedSeats={selectedSeats}
        onToggleSeat={handleToggleSeat}
        onReserve={handleReserveSeats}
        loading={reserveLoading}
      />
    </div>
  );
};
