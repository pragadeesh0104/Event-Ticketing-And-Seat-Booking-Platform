import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import { Ticket, User, LogOut, LayoutDashboard, Shield, Calendar } from 'lucide-react';

export const Navbar = () => {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark sticky-top shadow-sm py-3">
      <div className="container">
        <Link className="navbar-brand fw-bold text-primary d-flex align-items-center gap-2 fs-4" to="/">
          <Ticket className="text-primary" size={28} />
          <span>Eventify</span>
        </Link>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <Link className="nav-link text-light" to="/events">
                <Calendar size={18} className="me-1" /> Browse Events
              </Link>
            </li>
          </ul>

          <div className="d-flex align-items-center gap-3">
            {user ? (
              <>
                {user.role === 'ROLE_CUSTOMER' && (
                  <Link to="/my-bookings" className="btn btn-outline-light btn-sm d-flex align-items-center gap-1">
                    <Ticket size={16} /> My Bookings
                  </Link>
                )}
                {(user.role === 'ROLE_ORGANIZER' || user.role === 'ROLE_ADMIN') && (
                  <Link to="/organizer/dashboard" className="btn btn-outline-info btn-sm d-flex align-items-center gap-1">
                    <LayoutDashboard size={16} /> Organizer Portal
                  </Link>
                )}
                {user.role === 'ROLE_ADMIN' && (
                  <Link to="/admin/dashboard" className="btn btn-outline-warning btn-sm d-flex align-items-center gap-1">
                    <Shield size={16} /> Admin Portal
                  </Link>
                )}

                <span className="text-light small d-none d-md-inline">
                  <User size={14} className="me-1 text-primary" /> {user.fullName}
                </span>

                <button onClick={handleLogout} className="btn btn-danger btn-sm d-flex align-items-center gap-1">
                  <LogOut size={16} /> Logout
                </button>
              </>
            ) : (
              <>
                <Link to="/login" className="btn btn-outline-light btn-sm px-3">
                  Log In
                </Link>
                <Link to="/register" className="btn btn-primary btn-sm px-3 fw-semibold">
                  Get Started
                </Link>
              </>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};
