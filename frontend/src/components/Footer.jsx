import React from 'react';
import { Ticket, Github, Mail, ShieldCheck } from 'lucide-react';

export const Footer = () => {
  return (
    <footer className="bg-dark text-light py-5 mt-auto border-top border-secondary">
      <div className="container">
        <div className="row g-4">
          <div className="col-md-4">
            <h5 className="text-primary fw-bold d-flex align-items-center gap-2 mb-3">
              <Ticket size={24} /> Eventify
            </h5>
            <p className="text-muted small">
              Semester 5 Full-Stack Capstone Project. Event ticketing and real-time seat booking platform built with Spring Boot 3.x, MySQL 8, and React.js.
            </p>
          </div>
          <div className="col-md-3 ms-auto">
            <h6 className="text-white fw-bold mb-3">Quick Links</h6>
            <ul className="list-unstyled text-muted small">
              <li className="mb-2"><a href="/events" className="text-decoration-none text-muted">Browse Events</a></li>
              <li className="mb-2"><a href="/login" className="text-decoration-none text-muted">Account Login</a></li>
              <li className="mb-2"><a href="/register" className="text-decoration-none text-muted">Register Account</a></li>
            </ul>
          </div>
          <div className="col-md-3">
            <h6 className="text-white fw-bold mb-3">Academic Demo Notice</h6>
            <p className="text-muted small">
              <ShieldCheck className="text-success me-1 inline" size={16} /> All payment transactions use an idempotent simulated sandbox. No real monetary transactions take place.
            </p>
          </div>
        </div>
        <hr className="border-secondary my-4" />
        <div className="d-flex flex-column flex-sm-row justify-content-between text-muted small align-items-center">
          <p className="mb-0">© 2026 Eventify Platform. All rights reserved.</p>
          <div className="d-flex gap-3">
            <span className="badge bg-secondary">Java 17 / Spring Boot 3.x</span>
            <span className="badge bg-secondary">MySQL 8.0</span>
            <span className="badge bg-secondary">React + Vite</span>
          </div>
        </div>
      </div>
    </footer>
  );
};
