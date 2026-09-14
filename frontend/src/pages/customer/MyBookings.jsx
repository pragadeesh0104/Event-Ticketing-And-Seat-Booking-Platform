import React, { useState, useEffect } from 'react';
import API from '../../api/axios';
import { Ticket, Calendar, MapPin, QrCode, Printer, CheckCircle } from 'lucide-react';

export const MyBookings = () => {
  const [bookings, setBookings] = useState([]);
  const [selectedTicket, setSelectedTicket] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchMyBookings();
  }, []);

  const fetchMyBookings = async () => {
    try {
      const res = await API.get('/bookings/my');
      if (res.data.success) {
        setBookings(res.data.data || []);
      }
    } catch (err) {
      console.error('Failed to load user bookings:', err);
    } finally {
      setLoading(false);
    }
  };

  const handlePrint = () => {
    window.print();
  };

  return (
    <div className="container py-5">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h3 className="fw-bold mb-0 d-flex align-items-center gap-2 text-dark">
          <Ticket className="text-primary" /> My Confirmed Bookings & Digital Tickets
        </h3>
      </div>

      {loading ? (
        <div className="text-center py-5">
          <div className="spinner-border text-primary" role="status"></div>
        </div>
      ) : bookings.length === 0 ? (
        <div className="card border-0 shadow-sm p-5 text-center">
          <h5>No Bookings Found</h5>
          <p className="text-muted">You have not booked any event tickets yet.</p>
        </div>
      ) : (
        <div className="row g-4">
          {bookings.map((booking) => (
            <div key={booking.id} className="col-12">
              <div className="card border-0 shadow-sm rounded-4 overflow-hidden">
                <div className="card-header bg-dark text-white d-flex justify-content-between align-items-center py-3">
                  <div>
                    <span className="text-muted small">Booking Ref:</span>{' '}
                    <span className="font-monospace fw-bold text-primary">{booking.bookingReference}</span>
                  </div>
                  <span className="badge bg-success d-flex align-items-center gap-1">
                    <CheckCircle size={14} /> {booking.status}
                  </span>
                </div>

                <div className="card-body p-4">
                  <div className="row align-items-center">
                    <div className="col-md-7 mb-3 mb-md-0">
                      <h4 className="fw-bold text-dark mb-2">{booking.eventTitle}</h4>
                      <div className="d-flex flex-wrap gap-4 text-muted small">
                        <div><MapPin size={16} className="text-primary me-1" /> {booking.venueName}, {booking.venueCity}</div>
                        <div><Calendar size={16} className="text-primary me-1" /> {new Date(booking.eventStartTime).toLocaleString()}</div>
                      </div>
                    </div>

                    <div className="col-md-5 text-md-end">
                      <span className="text-muted small d-block">Total Paid</span>
                      <span className="fs-3 fw-bold text-success">${booking.totalAmount?.toFixed(2)}</span>
                    </div>
                  </div>

                  <hr className="my-3" />

                  {/* Tickets List */}
                  <h6 className="fw-bold mb-3">Issued Digital Tickets ({booking.tickets?.length})</h6>
                  <div className="row g-3">
                    {booking.tickets?.map((ticket) => (
                      <div key={ticket.id} className="col-md-6 col-lg-4">
                        <div className="border rounded-3 p-3 bg-light d-flex justify-content-between align-items-center">
                          <div>
                            <span className="badge bg-secondary mb-1">{ticket.sectionName} Section</span>
                            <div className="fw-bold">Row {ticket.rowLabel} • Seat {ticket.seatNumber}</div>
                            <div className="font-monospace small text-muted">{ticket.ticketNumber}</div>
                          </div>
                          <button
                            onClick={() => setSelectedTicket(ticket)}
                            className="btn btn-outline-primary btn-sm d-flex align-items-center gap-1"
                            data-bs-toggle="modal"
                            data-bs-target="#ticketModal"
                          >
                            <QrCode size={16} /> QR Pass
                          </button>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Ticket QR Modal */}
      {selectedTicket && (
        <div className="modal fade show d-block bg-dark bg-opacity-50" tabIndex="-1">
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content border-0 shadow-lg rounded-4 overflow-hidden">
              <div className="modal-header bg-dark text-white">
                <h5 className="modal-title font-monospace">VERIFIABLE DIGITAL TICKET</h5>
                <button type="button" className="btn-close btn-close-white" onClick={() => setSelectedTicket(null)}></button>
              </div>
              <div className="modal-body p-4 text-center">
                <h4 className="fw-bold text-dark mb-1">{selectedTicket.eventTitle}</h4>
                <p className="text-muted small mb-3">{selectedTicket.venueName}</p>

                {/* QR Image */}
                {selectedTicket.qrCodeBase64 && (
                  <img
                    src={selectedTicket.qrCodeBase64}
                    alt="Ticket QR Code"
                    className="img-fluid border p-2 bg-white rounded-3 shadow-sm mb-3"
                    style={{ width: '220px', height: '220px' }}
                  />
                )}

                <div className="bg-light p-3 rounded-3 mb-3 border text-start small">
                  <div><strong>Ticket Number:</strong> <span className="font-monospace text-primary">{selectedTicket.ticketNumber}</span></div>
                  <div><strong>Seat Allocation:</strong> Row {selectedTicket.rowLabel} Seat {selectedTicket.seatNumber}</div>
                  <div><strong>Status:</strong> <span className="badge bg-success">{selectedTicket.status}</span></div>
                </div>

                <div className="d-flex gap-2">
                  <button onClick={handlePrint} className="btn btn-outline-dark w-100 d-flex align-items-center justify-content-center gap-2">
                    <Printer size={18} /> Print Ticket
                  </button>
                  <button onClick={() => setSelectedTicket(null)} className="btn btn-secondary w-100">
                    Close
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
