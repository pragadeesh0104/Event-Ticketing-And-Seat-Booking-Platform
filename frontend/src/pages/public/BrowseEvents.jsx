import React, { useState, useEffect } from 'react';
import { useSearchParams, Link } from 'react-router-dom';
import API from '../../api/axios';
import { Search, MapPin, Calendar, Ticket, Filter } from 'lucide-react';

export const BrowseEvents = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const [events, setEvents] = useState([]);
  const [keyword, setKeyword] = useState(searchParams.get('keyword') || '');
  const [category, setCategory] = useState(searchParams.get('category') || '');
  const [city, setCity] = useState(searchParams.get('city') || '');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchEvents();
  }, [searchParams]);

  const fetchEvents = async () => {
    setLoading(true);
    try {
      const qK = searchParams.get('keyword') || '';
      const qC = searchParams.get('category') || '';
      const qCi = searchParams.get('city') || '';

      const res = await API.get(`/events?keyword=${qK}&category=${qC}&city=${qCi}&page=0&size=20`);
      if (res.data.success) {
        setEvents(res.data.data.content || []);
      }
    } catch (err) {
      console.error('Failed to fetch events:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleFilterSubmit = (e) => {
    e.preventDefault();
    const params = {};
    if (keyword) params.keyword = keyword;
    if (category) params.category = category;
    if (city) params.city = city;
    setSearchParams(params);
  };

  return (
    <div className="container py-5">
      <div className="row g-4">
        {/* Filters Sidebar */}
        <div className="col-lg-3">
          <div className="card shadow-sm border-0 rounded-3 p-4 sticky-top" style={{ top: '100px' }}>
            <h5 className="fw-bold mb-3 d-flex align-items-center gap-2 text-dark">
              <Filter size={18} className="text-primary" /> Filter Events
            </h5>
            <form onSubmit={handleFilterSubmit}>
              <div className="mb-3">
                <label className="form-label small fw-semibold">Search Keyword</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Event title..."
                  value={keyword}
                  onChange={(e) => setKeyword(e.target.value)}
                />
              </div>

              <div className="mb-3">
                <label className="form-label small fw-semibold">Category</label>
                <select className="form-select" value={category} onChange={(e) => setCategory(e.target.value)}>
                  <option value="">All Categories</option>
                  <option value="Music">Music</option>
                  <option value="Tech">Tech & Software</option>
                  <option value="Sports">Sports</option>
                  <option value="Arts">Arts & Theatre</option>
                </select>
              </div>

              <div className="mb-3">
                <label className="form-label small fw-semibold">City / Location</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="New York, San Francisco..."
                  value={city}
                  onChange={(e) => setCity(e.target.value)}
                />
              </div>

              <button type="submit" className="btn btn-primary w-100 fw-semibold mt-2">
                Apply Filters
              </button>
            </form>
          </div>
        </div>

        {/* Event Listings Grid */}
        <div className="col-lg-9">
          <div className="d-flex justify-content-between align-items-center mb-4">
            <h4 className="fw-bold mb-0">Events Directory ({events.length})</h4>
          </div>

          {loading ? (
            <div className="text-center py-5">
              <div className="spinner-border text-primary" role="status"></div>
            </div>
          ) : events.length === 0 ? (
            <div className="card border-0 shadow-sm p-5 text-center">
              <p className="text-muted mb-0">No published events found matching your criteria.</p>
            </div>
          ) : (
            <div className="row g-4">
              {events.map((event) => (
                <div key={event.id} className="col-md-6">
                  <div className="card h-100 border-0 shadow-sm rounded-3 overflow-hidden">
                    <img
                      src={event.posterUrl || 'https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&w=800&q=80'}
                      alt={event.title}
                      className="card-img-top"
                      style={{ height: '180px', objectFit: 'cover' }}
                    />
                    <div className="card-body d-flex flex-column p-4">
                      <div className="d-flex justify-content-between align-items-center mb-2">
                        <span className="badge bg-info text-dark">{event.category}</span>
                        <span className="fw-bold text-success fs-5">${event.minPrice}</span>
                      </div>
                      <h5 className="card-title fw-bold text-dark mb-2">{event.title}</h5>
                      <p className="card-text text-muted small mb-3 flex-grow-1">
                        {event.description}
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
      </div>
    </div>
  );
};
