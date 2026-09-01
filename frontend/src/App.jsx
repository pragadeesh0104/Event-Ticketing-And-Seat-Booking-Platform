import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { Navbar } from './components/Navbar';
import { Footer } from './components/Footer';
import { Home } from './pages/public/Home';
import { BrowseEvents } from './pages/public/BrowseEvents';
import { EventDetails } from './pages/public/EventDetails';
import { Login } from './pages/public/Login';
import { Register } from './pages/public/Register';
import { Checkout } from './pages/customer/Checkout';
import { MyBookings } from './pages/customer/MyBookings';
import { OrganizerDashboard } from './pages/organizer/OrganizerDashboard';
import { AdminDashboard } from './pages/admin/AdminDashboard';

export default function App() {
  return (
    <div className="d-flex flex-column min-vh-100 bg-light">
      <Navbar />
      <main className="flex-grow-1">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/events" element={<BrowseEvents />} />
          <Route path="/events/:id" element={<EventDetails />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/checkout/:reservationId" element={<Checkout />} />
          <Route path="/my-bookings" element={<MyBookings />} />
          <Route path="/organizer/dashboard" element={<OrganizerDashboard />} />
          <Route path="/admin/dashboard" element={<AdminDashboard />} />
        </Routes>
      </main>
      <Footer />
    </div>
  );
}
