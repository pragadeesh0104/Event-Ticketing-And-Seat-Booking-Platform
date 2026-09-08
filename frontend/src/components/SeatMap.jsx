import React from 'react';
import { CheckCircle2, AlertCircle, Clock } from 'lucide-react';

export const SeatMap = ({ seats, selectedSeats, onToggleSeat, onReserve, loading }) => {
  // Group seats by Section and Row
  const rows = Array.from(new Set(seats.map(s => s.rowLabel))).sort();

  const getSeatColor = (seat) => {
    if (seat.status === 'BOOKED') return 'btn-secondary disabled opacity-50';
    if (seat.status === 'HELD') return 'btn-warning text-dark disabled';
    if (selectedSeats.some(s => s.id === seat.id)) return 'btn-primary active border-3 border-white shadow';
    if (seat.sectionName === 'VIP') return 'btn-outline-danger';
    if (seat.sectionName === 'PREMIUM') return 'btn-outline-warning';
    return 'btn-outline-success';
  };

  const totalPrice = selectedSeats.reduce((sum, s) => sum + (s.price || 50), 0);

  return (
    <div className="card shadow-sm border-0 mb-4">
      <div className="card-header bg-dark text-white d-flex justify-content-between align-items-center py-3">
        <h5 className="mb-0 font-monospace">STAGE / SCREEN THIS WAY</h5>
        <span className="badge bg-primary">Interactive Seat Layout Map</span>
      </div>
      <div className="card-body p-4">
        {/* Stage Line */}
        <div className="w-100 bg-secondary text-white text-center py-2 rounded mb-5 fw-bold letter-spacing-2">
          STAGE AREA
        </div>

        {/* Legend */}
        <div className="d-flex flex-wrap justify-content-center gap-4 mb-4 small bg-light p-3 rounded">
          <div className="d-flex align-items-center gap-1">
            <span className="badge bg-outline-success border border-success text-success px-2 py-1">Available</span>
          </div>
          <div className="d-flex align-items-center gap-1">
            <span className="badge bg-primary px-2 py-1">Selected</span>
          </div>
          <div className="d-flex align-items-center gap-1">
            <span className="badge bg-warning text-dark px-2 py-1">Held (10-min limit)</span>
          </div>
          <div className="d-flex align-items-center gap-1">
            <span className="badge bg-secondary px-2 py-1">Booked</span>
          </div>
        </div>

        {/* Seat Grid */}
        <div className="d-flex flex-column align-items-center gap-3 overflow-auto py-2">
          {rows.map(rowLabel => {
            const rowSeats = seats.filter(s => s.rowLabel === rowLabel)
              .sort((a, b) => parseInt(a.seatNumber) - parseInt(b.seatNumber));
            return (
              <div key={rowLabel} className="d-flex align-items-center gap-2">
                <span className="fw-bold text-muted me-2" style={{ width: '20px' }}>{rowLabel}</span>
                <div className="d-flex gap-2">
                  {rowSeats.map(seat => {
                    const isSelected = selectedSeats.some(s => s.id === seat.id);
                    return (
                      <button
                        key={seat.id}
                        type="button"
                        onClick={() => onToggleSeat(seat)}
                        disabled={seat.status === 'BOOKED' || seat.status === 'HELD'}
                        className={`btn btn-sm ${getSeatColor(seat)} fw-semibold`}
                        style={{ width: '42px', height: '42px' }}
                        title={`${seat.sectionName} - Row ${seat.rowLabel} Seat ${seat.seatNumber} ($${seat.price})`}
                      >
                        {seat.seatNumber}
                      </button>
                    );
                  })}
                </div>
              </div>
            );
          })}
        </div>

        {/* Selection Summary */}
        <hr className="my-4" />
        <div className="d-flex flex-column flex-md-row justify-content-between align-items-center gap-3 bg-light p-3 rounded">
          <div>
            <h6 className="mb-1 text-dark fw-bold">Selected Seats ({selectedSeats.length})</h6>
            <p className="mb-0 text-muted small">
              {selectedSeats.length > 0
                ? selectedSeats.map(s => `${s.sectionName} ${s.rowLabel}-${s.seatNumber} ($${s.price})`).join(', ')
                : 'No seats selected yet. Click available seats above.'}
            </p>
          </div>
          <div className="d-flex align-items-center gap-3">
            <div className="text-end">
              <span className="text-muted small d-block">Total Price</span>
              <span className="fs-4 fw-bold text-success">${totalPrice.toFixed(2)}</span>
            </div>
            <button
              onClick={onReserve}
              disabled={selectedSeats.length === 0 || loading}
              className="btn btn-success btn-lg px-4 d-flex align-items-center gap-2 fw-semibold"
            >
              {loading ? (
                <>Hold Seats...</>
              ) : (
                <>
                  <Clock size={18} /> Reserve Seats (10-Min Hold)
                </>
              )}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
