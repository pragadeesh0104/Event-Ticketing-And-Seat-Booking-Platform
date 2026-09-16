import React, { useState, useEffect } from 'react';
import API from '../../api/axios';
import { Calendar, Ticket, DollarSign, PlusCircle, BarChart3 } from 'lucide-react';
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer } from 'recharts';

export const OrganizerDashboard = () => {
  const [stats, setStats] = useState(null);
  const [venues, setVenues] = useState([]);
  const [loading, setLoading] = useState(true);

  // New Event Form State
  const [title, setTitle] = useState('');
  const [category, setCategory] = useState('Music');
  const [venueId, setVenueId] = useState('');
  const [posterUrl, setPosterUrl] = useState('');
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');
  const [msg, setMsg] = useState('');

  useEffect(() => {
    fetchOrganizerData();
  }, []);

  const fetchOrganizerData = async () => {
    try {
      const [dashRes, venuesRes] = await Promise.all([
        API.get('/organizer/dashboard'),
        API.get('/venues')
      ]);

      if (dashRes.data.success) {
        setStats(dashRes.data.data);
      }
      if (venuesRes.data.success) {
        setVenues(venuesRes.data.data || []);
        if (venuesRes.data.data.length > 0) setVenueId(venuesRes.data.data[0].id);
      }
    } catch (err) {
      console.error('Failed to load organizer dashboard:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateEvent = async (e) => {
    e.preventDefault();
    setMsg('');
    try {
      const res = await API.post('/events', {
        venueId: parseInt(venueId),
        title,
        category,
        posterUrl,
        startTime,
        endTime
      });

      if (res.data.success) {
        setMsg('Event created successfully in DRAFT mode!');
        setTitle('');
        fetchOrganizerData();
      }
    } catch (err) {
      setMsg(err.response?.data?.message || 'Failed to create event.');
    }
  };

  const chartData = [
    { name: 'Events', count: stats?.totalPublishedEvents || 0 },
    { name: 'Bookings', count: stats?.totalBookingsCount || 0 },
    { name: 'Tickets Sold', count: stats?.totalTicketsSold || 0 }
  ];

  if (loading) {
    return (
      <div className="text-center py-5">
        <div className="spinner-border text-primary" role="status"></div>
      </div>
    );
  }

  return (
    <div className="container py-5">
      <h3 className="fw-bold mb-4 text-dark d-flex align-items-center gap-2">
        <BarChart3 className="text-primary" /> Organizer Dashboard & Sales Analytics
      </h3>

      {/* Metrics Cards */}
      <div className="row g-4 mb-5">
        <div className="col-md-3">
          <div className="card border-0 shadow-sm rounded-3 p-4 bg-primary text-white">
            <h6 className="text-white-50">Total Events</h6>
            <h2 className="fw-bold mb-0">{stats?.totalPublishedEvents || 0}</h2>
          </div>
        </div>
        <div className="col-md-3">
          <div className="card border-0 shadow-sm rounded-3 p-4 bg-success text-white">
            <h6 className="text-white-50">Total Revenue</h6>
            <h2 className="fw-bold mb-0">${stats?.totalRevenue?.toFixed(2) || '0.00'}</h2>
          </div>
        </div>
        <div className="col-md-3">
          <div className="card border-0 shadow-sm rounded-3 p-4 bg-warning text-dark">
            <h6 className="text-dark-50">Tickets Sold</h6>
            <h2 className="fw-bold mb-0">{stats?.totalTicketsSold || 0}</h2>
          </div>
        </div>
        <div className="col-md-3">
          <div className="card border-0 shadow-sm rounded-3 p-4 bg-info text-white">
            <h6 className="text-white-50">Confirmed Bookings</h6>
            <h2 className="fw-bold mb-0">{stats?.totalBookingsCount || 0}</h2>
          </div>
        </div>
      </div>

      <div className="row g-4">
        {/* Recharts Sales Chart */}
        <div className="col-lg-6">
          <div className="card border-0 shadow-sm rounded-4 p-4 h-100">
            <h5 className="fw-bold mb-4">Event Booking Performance</h5>
            <ResponsiveContainer width="100%" height={260}>
              <BarChart data={chartData}>
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Bar dataKey="count" fill="#0d6efd" radius={[6, 6, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>

        {/* Create Event Form */}
        <div className="col-lg-6">
          <div className="card border-0 shadow-sm rounded-4 p-4">
            <h5 className="fw-bold mb-3 d-flex align-items-center gap-2">
              <PlusCircle className="text-primary" /> Create New Event Listing
            </h5>

            {msg && <div className="alert alert-info small mb-3">{msg}</div>}

            <form onSubmit={handleCreateEvent}>
              <div className="mb-3">
                <label className="form-label small fw-semibold">Event Title</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="e.g. Summer Rock Concert"
                  required
                  value={title}
                  onChange={(e) => setTitle(e.target.value)}
                />
              </div>

              <div className="row g-2 mb-3">
                <div className="col-6">
                  <label className="form-label small fw-semibold">Category</label>
                  <select className="form-select" value={category} onChange={(e) => setCategory(e.target.value)}>
                    <option value="Music">Music</option>
                    <option value="Tech">Tech</option>
                    <option value="Sports">Sports</option>
                    <option value="Arts">Arts</option>
                  </select>
                </div>
                <div className="col-6">
                  <label className="form-label small fw-semibold">Venue</label>
                  <select className="form-select" value={venueId} onChange={(e) => setVenueId(e.target.value)}>
                    {venues.map(v => (
                      <option key={v.id} value={v.id}>{v.name} ({v.city})</option>
                    ))}
                  </select>
                </div>
              </div>

              <div className="mb-3">
                <label className="form-label small fw-semibold">Poster URL</label>
                <input
                  type="url"
                  className="form-control"
                  placeholder="https://images.unsplash.com/..."
                  value={posterUrl}
                  onChange={(e) => setPosterUrl(e.target.value)}
                />
              </div>

              <div className="row g-2 mb-4">
                <div className="col-6">
                  <label className="form-label small fw-semibold">Start Time</label>
                  <input
                    type="datetime-local"
                    className="form-control"
                    required
                    value={startTime}
                    onChange={(e) => setStartTime(e.target.value)}
                  />
                </div>
                <div className="col-6">
                  <label className="form-label small fw-semibold">End Time</label>
                  <input
                    type="datetime-local"
                    className="form-control"
                    required
                    value={endTime}
                    onChange={(e) => setEndTime(e.target.value)}
                  />
                </div>
              </div>

              <button type="submit" className="btn btn-primary w-100 fw-semibold">
                Create Event
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};
