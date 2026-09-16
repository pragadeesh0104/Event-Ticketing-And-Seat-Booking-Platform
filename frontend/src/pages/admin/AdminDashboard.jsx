import React, { useState, useEffect } from 'react';
import API from '../../api/axios';
import { Shield, Users, Calendar, MapPin, DollarSign, CheckCircle, Ban } from 'lucide-react';

export const AdminDashboard = () => {
  const [stats, setStats] = useState(null);
  const [usersList, setUsersList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAdminData();
  }, []);

  const fetchAdminData = async () => {
    try {
      const [statsRes, usersRes] = await Promise.all([
        API.get('/admin/dashboard'),
        API.get('/admin/users')
      ]);

      if (statsRes.data.success) {
        setStats(statsRes.data.data);
      }
      if (usersRes.data.success) {
        setUsersList(usersRes.data.data || []);
      }
    } catch (err) {
      console.error('Failed to load admin dashboard:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleToggleStatus = async (userId, currentStatus) => {
    const nextStatus = currentStatus === 'ACTIVE' ? 'SUSPENDED' : 'ACTIVE';
    try {
      const res = await API.patch(`/admin/users/${userId}/status?status=${nextStatus}`);
      if (res.data.success) {
        fetchAdminData();
      }
    } catch (err) {
      console.error('Failed to toggle user status:', err);
    }
  };

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
        <Shield className="text-warning" /> Administrator Governance Dashboard
      </h3>

      {/* Metrics Cards */}
      <div className="row g-4 mb-5">
        <div className="col-md-4 col-lg-2">
          <div className="card border-0 shadow-sm p-3 bg-dark text-white rounded-3">
            <span className="small text-muted">Total Users</span>
            <h3 className="fw-bold mb-0">{stats?.totalUsers || 0}</h3>
          </div>
        </div>
        <div className="col-md-4 col-lg-2">
          <div className="card border-0 shadow-sm p-3 bg-primary text-white rounded-3">
            <span className="small text-white-50">Organizers</span>
            <h3 className="fw-bold mb-0">{stats?.totalOrganizers || 0}</h3>
          </div>
        </div>
        <div className="col-md-4 col-lg-2">
          <div className="card border-0 shadow-sm p-3 bg-info text-white rounded-3">
            <span className="small text-white-50">Total Events</span>
            <h3 className="fw-bold mb-0">{stats?.totalEvents || 0}</h3>
          </div>
        </div>
        <div className="col-md-4 col-lg-2">
          <div className="card border-0 shadow-sm p-3 bg-secondary text-white rounded-3">
            <span className="small text-white-50">Venues</span>
            <h3 className="fw-bold mb-0">{stats?.totalVenues || 0}</h3>
          </div>
        </div>
        <div className="col-md-4 col-lg-2">
          <div className="card border-0 shadow-sm p-3 bg-warning text-dark rounded-3">
            <span className="small text-dark-50">Confirmed Bookings</span>
            <h3 className="fw-bold mb-0">{stats?.totalConfirmedBookings || 0}</h3>
          </div>
        </div>
        <div className="col-md-4 col-lg-2">
          <div className="card border-0 shadow-sm p-3 bg-success text-white rounded-3">
            <span className="small text-white-50">Platform Revenue</span>
            <h4 className="fw-bold mb-0">${stats?.totalPlatformRevenue?.toFixed(2) || '0.00'}</h4>
          </div>
        </div>
      </div>

      {/* User Management Table */}
      <div className="card border-0 shadow-sm rounded-4 overflow-hidden">
        <div className="card-header bg-dark text-white py-3">
          <h5 className="mb-0 fw-bold">Platform User Management ({usersList.length})</h5>
        </div>
        <div className="card-body p-0">
          <div className="table-responsive">
            <table className="table table-hover align-middle mb-0">
              <thead className="table-light">
                <tr>
                  <th>ID</th>
                  <th>Full Name</th>
                  <th>Email</th>
                  <th>Role</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {usersList.map((u) => (
                  <tr key={u.id}>
                    <td>{u.id}</td>
                    <td className="fw-bold">{u.fullName}</td>
                    <td>{u.email}</td>
                    <td>
                      <span className={`badge ${u.role === 'ROLE_ADMIN' ? 'bg-warning text-dark' : u.role === 'ROLE_ORGANIZER' ? 'bg-info text-dark' : 'bg-secondary'}`}>
                        {u.role}
                      </span>
                    </td>
                    <td>
                      <span className={`badge ${u.accountStatus === 'ACTIVE' ? 'bg-success' : 'bg-danger'}`}>
                        {u.accountStatus}
                      </span>
                    </td>
                    <td>
                      <button
                        onClick={() => handleToggleStatus(u.id, u.accountStatus)}
                        className={`btn btn-sm ${u.accountStatus === 'ACTIVE' ? 'btn-outline-danger' : 'btn-outline-success'} fw-semibold`}
                      >
                        {u.accountStatus === 'ACTIVE' ? <><Ban size={14} /> Suspend</> : <><CheckCircle size={14} /> Activate</>}
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};
