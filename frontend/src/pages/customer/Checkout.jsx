import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import API from '../../api/axios';
import { Clock, CreditCard, ShieldCheck, AlertTriangle, CheckCircle2 } from 'lucide-react';

export const Checkout = () => {
  const { reservationId } = useParams();
  const navigate = useNavigate();

  const [reservation, setReservation] = useState(null);
  const [secondsRemaining, setSecondsRemaining] = useState(600);
  const [simulateFailure, setSimulateFailure] = useState(false);
  const [loading, setLoading] = useState(true);
  const [payLoading, setPayLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');

  useEffect(() => {
    fetchReservation();
  }, [reservationId]);

  useEffect(() => {
    if (secondsRemaining <= 0) return;
    const interval = setInterval(() => {
      setSecondsRemaining(prev => Math.max(0, prev - 1));
    }, 1000);
    return () => clearInterval(interval);
  }, [secondsRemaining]);

  const fetchReservation = async () => {
    try {
      const res = await API.get(`/reservations/${reservationId}`);
      if (res.data.success) {
        setReservation(res.data.data);
        setSecondsRemaining(res.data.data.holdDurationSeconds || 600);
      }
    } catch (err) {
      setErrorMsg('Reservation not found or expired.');
    } finally {
      setLoading(false);
    }
  };

  const handleProcessPayment = async (e) => {
    e.preventDefault();
    setPayLoading(true);
    setErrorMsg('');

    try {
      const idempotencyKey = `IDEM-${Date.now()}-${Math.random().toString(36).substring(2, 7)}`;
      const res = await API.post('/bookings/checkout', {
        reservationId: parseInt(reservationId),
        paymentMethod: 'DEMO_CARD',
        idempotencyKey,
        simulateFailure
      });

      if (res.data.success) {
        navigate('/my-bookings');
      }
    } catch (err) {
      setErrorMsg(err.response?.data?.message || 'Payment processing failed. Please try again.');
    } finally {
      setPayLoading(false);
    }
  };

  const formatTimer = (totalSec) => {
    const mins = Math.floor(totalSec / 60);
    const secs = totalSec % 60;
    return `${mins}:${secs < 10 ? '0' : ''}${secs}`;
  };

  if (loading) {
    return (
      <div className="text-center py-5">
        <div className="spinner-border text-primary" role="status"></div>
      </div>
    );
  }

  if (!reservation) {
    return (
      <div className="container py-5 text-center">
        <h4>Reservation Expired or Not Found</h4>
        <button onClick={() => navigate('/events')} className="btn btn-primary mt-3">Browse Events</button>
      </div>
    );
  }

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-lg-8">
          {/* Hold Countdown Warning */}
          <div className={`alert ${secondsRemaining < 120 ? 'alert-danger' : 'alert-warning'} d-flex align-items-center justify-content-between mb-4 shadow-sm`}>
            <div className="d-flex align-items-center gap-2">
              <Clock size={24} />
              <div>
                <strong>Seats Temporarily Held for You!</strong>
                <div className="small">Complete payment before timer expires to confirm your tickets.</div>
              </div>
            </div>
            <div className="fs-3 fw-bold font-monospace">{formatTimer(secondsRemaining)}</div>
          </div>

          {errorMsg && <div className="alert alert-danger mb-4">{errorMsg}</div>}

          <div className="row g-4">
            {/* Booking Breakdown */}
            <div className="col-md-6">
              <div className="card border-0 shadow-sm rounded-3 p-4 h-100">
                <h5 className="fw-bold mb-3 border-bottom pb-2">Reservation Summary</h5>
                <p className="mb-2"><strong>Event:</strong> {reservation.eventTitle}</p>
                <p className="mb-2"><strong>Reference:</strong> <span className="font-monospace text-primary">{reservation.reservationReference}</span></p>
                <p className="mb-2"><strong>Selected Seats:</strong> {reservation.seatIds?.length} Seats</p>
                <hr />
                <div className="d-flex justify-content-between fs-5 fw-bold text-dark">
                  <span>Total Amount:</span>
                  <span className="text-success">${reservation.totalAmount?.toFixed(2)}</span>
                </div>
              </div>
            </div>

            {/* Payment Sandbox Form */}
            <div className="col-md-6">
              <div className="card border-0 shadow-sm rounded-3 p-4">
                <h5 className="fw-bold mb-3 d-flex align-items-center gap-2">
                  <CreditCard className="text-primary" /> Demo Payment Gateway
                </h5>
                <p className="small text-muted mb-3">
                  <ShieldCheck className="text-success inline me-1" size={14} /> Simulated Sandbox Mode (No real credit card charged).
                </p>

                <form onSubmit={handleProcessPayment}>
                  <div className="mb-3">
                    <label className="form-label small">Simulated Card Number</label>
                    <input type="text" className="form-control" defaultValue="4242 •••• •••• 4242" disabled />
                  </div>

                  <div className="row g-2 mb-3">
                    <div className="col-6">
                      <label className="form-label small">Expiry Date</label>
                      <input type="text" className="form-control" defaultValue="12/28" disabled />
                    </div>
                    <div className="col-6">
                      <label className="form-label small">CVV</label>
                      <input type="password" className="form-control" defaultValue="123" disabled />
                    </div>
                  </div>

                  {/* Failure Simulation Toggle for QA Review */}
                  <div className="form-check mb-4 bg-light p-2 rounded ps-4 border">
                    <input
                      type="checkbox"
                      className="form-check-input"
                      id="simulateFail"
                      checked={simulateFailure}
                      onChange={(e) => setSimulateFailure(e.target.checked)}
                    />
                    <label className="form-check-label small text-danger fw-semibold" htmlFor="simulateFail">
                      Simulate Payment Failure Scenario (Testing Mode)
                    </label>
                  </div>

                  <button
                    type="submit"
                    disabled={payLoading || secondsRemaining <= 0}
                    className="btn btn-success w-100 btn-lg fw-semibold d-flex align-items-center justify-content-center gap-2"
                  >
                    {payLoading ? 'Settling Payment...' : <><CheckCircle2 size={20} /> Pay ${reservation.totalAmount?.toFixed(2)} & Issue Tickets</>}
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
