import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import API from '../../api/axios';
import { Search, Calendar, MapPin, Ticket, Sparkles, ShieldCheck, Zap } from 'lucide-react';

export const Home = () => {
  const [events, setEvents] = useState([]);
  const [recommendations, setRecommendations] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState('');
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetchInitialData();
  }, []);

  const fetchInitialData = async () => {
    try {
      const [eventsRes, recRes] = await Promise.all([
        API.get('/events?page=0&size=6'),
        API.get('/recommendations')
      ]);

      if (eventsRes.data.success) {
        setEvents(eventsRes.data.data.content || []);
      }
      if (recRes.data.success) {
        setRecommendations(recRes.data.data || []);
      }
    } catch (err) {
      console.error('Failed to load home page events:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    if (searchKeyword.trim()) {
      navigate(`/events?keyword=${encodeURIComponent(searchKeyword)}`);
    }
  };

  return (
    <div>
      {/* Hero Section */}
      <section className="bg-dark text-white py-5 position-relative overflow-hidden border-bottom border-secondary">
        <div className="container py-4">
          <div className="row align-items-center g-5">
            <div className="col-lg-7">
              <span className="badge bg-primary px-3 py-2 fs-6 mb-3 rounded-pill">
                <Sparkles size={16} className="me-1 inline" /> Real-Time Seat Booking Engine
              </span>
              <h1 className="display-4 fw-extrabold text-white mb-3">
                Discover Unforgettable Live Events & Reserve Seats Intantly
              </h1>
              <p className="lead text-muted mb-4">
                Experience seamless ticket reservations, interactive seat selection, instant QR code ticket issuance, and guaranteed zero double-bookings.
              </p>

              {/* Search Bar */}
              <form onSubmit={handleSearchSubmit} className="bg-white p-2 rounded-3 shadow-lg d-flex gap-2 align-items-center">
                <Search className="text-muted ms-2" size={24} />
                <input
                  type="text"
                  className="form-control border-0 shadow-none text-dark fs-6"
                  placeholder="Search concert, conference, festival..."
                  value={searchKeyword}
                  onChange={(e) => setSearchKeyword(e.target.value)}
                />
                <button type="submit" className="btn btn-primary btn-lg px-4 fw-semibold">
                  Search
                </button>
              </form>
            </div>
            <div className="col-lg-5 d-none d-lg-block">
              <img
                src="https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&w=800&q=80"
                alt="Event Concert Banner"
                className="img-fluid rounded-4 shadow-lg border border-secondary"
              />
            </div>
          </div>
        </div>
      </section>

      {/* Featured Upcoming Events */}
      <section className="py-5 bg-light">
        <div className="container">
          <div className="d-flex justify-content-between align-items-end mb-4">
            <div>
              <span className="text-primary text-uppercase fw-bold fs-6">Live Listings</span>
              <h2 className="fw-bold mb-0">Upcoming Featured Events</h2>
            </div>
            <Link to="/events" className="btn btn-outline-primary fw-semibold">
              View All Events
            </Link>
          </div>

          {loading ? (
            <div className="text-center py-5">
              <div className="spinner-border text-primary" role="status"></div>
            </div>
          ) : (
            <div className="row g-4">
              {events.map((event) => (
                <div key={event.id} className="col-md-6 col-lg-4">
                  <div className="card h-100 border-0 shadow-sm rounded-3 overflow-hidden">
                    <img
                      src={event.posterUrl || 'https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&w=800&q=80'}
                      alt={event.title}
                      className="card-img-top"
                      style={{ height: '200px', objectFit: 'cover' }}
                    />
                    <div className="card-body d-flex flex-column p-4">
                      <div className="d-flex justify-content-between align-items-center mb-2">
                        <span className="badge bg-info text-dark">{event.category}</span>
                        <span className="fw-bold text-success fs-5">${event.minPrice}</span>
                      </div>
                      <h5 className="card-title fw-bold text-dark mb-2">{event.title}</h5>
                      <p className="card-text text-muted small mb-3 flex-grow-1">
                        {event.description?.substring(0, 90)}...
                      </p>
                      <div className="small text-muted mb-3">
                        <div className="d-flex align-items-center gap-2 mb-1">
                          <MapPin size={16} className="text-primary" /> {event.venueName}, {event.venueCity}
                        </div>
                        <div className="d-flex align-items-center gap-2">
                          <Calendar size={16} className="text-primary" /> {new Date(event.startTime).toLocaleString()}
                        </div>
                      </div>
                      <Link to={`/events/${event.id}`} className="btn btn-primary w-100 fw-semibold d-flex align-items-center justify-content-center gap-2">
                        <Ticket size={18} /> Select Seats & Book
                      </Link>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </section>

      {/* Smart Recommendations Section */}
      {recommendations.length > 0 && (
        <section className="py-5 bg-white border-top border-bottom">
          <div className="container">
            <div className="mb-4">
              <span className="badge bg-success mb-2 px-3 py-2">
                <Sparkles size={14} className="me-1 inline" /> Day 60 Smart Recommendation Enhancement
              </span>
              <h3 className="fw-bold">Smart Event Insights & Recommended For You</h3>
              <p className="text-muted small">Rule-based recommendations matching popular event categories and location availability.</p>
            </div>
            <div className="row g-4">
              {recommendations.map(event => (
                <div key={event.id} className="col-md-4">
                  <div className="card border-primary border-opacity-25 shadow-sm h-100 p-3">
                    <span className="badge bg-primary text-white w-fit mb-2">Recommended Category: {event.category}</span>
                    <h6 className="fw-bold text-dark">{event.title}</h6>
                    <p className="small text-muted">{event.venueName} • ${event.minPrice}</p>
                    <Link to={`/events/${event.id}`} className="btn btn-sm btn-outline-primary mt-auto">
                      View Event
                    </Link>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </section>
      )}

      {/* How it works */}
      <section className="py-5 bg-dark text-white">
        <div className="container py-3">
          <div className="text-center mb-5">
            <h2 className="fw-bold text-white">How Eventify Works</h2>
            <p className="text-muted">Four simple steps to secure your seats for live concerts, summits, and shows.</p>
          </div>
          <div className="row g-4 text-center">
            <div className="col-md-3">
              <div className="p-4 bg-secondary bg-opacity-25 rounded-4">
                <Search size={36} className="text-primary mb-3" />
                <h5 className="fw-bold">1. Discover Events</h5>
                <p className="small text-muted mb-0">Browse published events by city, category, or date range.</p>
              </div>
            </div>
            <div className="col-md-3">
              <div className="p-4 bg-secondary bg-opacity-25 rounded-4">
                <Ticket size={36} className="text-warning mb-3" />
                <h5 className="fw-bold">2. Select Venue Seats</h5>
                <p className="small text-muted mb-0">Pick your exact seat numbers on the interactive seating map.</p>
              </div>
            </div>
            <div className="col-md-3">
              <div className="p-4 bg-secondary bg-opacity-25 rounded-4">
                <Zap size={36} className="text-info mb-3" />
                <h5 className="fw-bold">3. 10-Min Temporary Hold</h5>
                <p className="small text-muted mb-0">Backend locks your seats for 10 minutes to complete checkout.</p>
              </div>
            </div>
            <div className="col-md-3">
              <div className="p-4 bg-secondary bg-opacity-25 rounded-4">
                <ShieldCheck size={36} className="text-success mb-3" />
                <h5 className="fw-bold">4. QR Ticket Issuance</h5>
                <p className="small text-muted mb-0">Receive digital tickets with verifiable QR code tokens for venue entry.</p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};
