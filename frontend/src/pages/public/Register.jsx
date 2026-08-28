import React, { useState, useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import { UserPlus, User, Mail, Key, Phone, Shield } from 'lucide-react';

export const Register = () => {
  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [phone, setPhone] = useState('');
  const [role, setRole] = useState('ROLE_CUSTOMER');
  const [errorMsg, setErrorMsg] = useState('');
  const [loading, setLoading] = useState(false);

  const { register } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setErrorMsg('');
    try {
      await register({ fullName, email, password, phone, role });
      navigate('/events');
    } catch (err) {
      setErrorMsg(err.response?.data?.message || 'Registration failed.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-md-7 col-lg-6">
          <div className="card border-0 shadow-lg rounded-4 p-4 p-md-5">
            <div className="text-center mb-4">
              <div className="bg-primary bg-opacity-10 text-primary p-3 rounded-circle d-inline-block mb-3">
                <UserPlus size={32} />
              </div>
              <h3 className="fw-bold">Create Account</h3>
              <p className="text-muted small">Register as a Customer or Event Organizer</p>
            </div>

            {errorMsg && <div className="alert alert-danger small mb-3">{errorMsg}</div>}

            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label small fw-semibold">Full Name</label>
                <div className="input-group">
                  <span className="input-group-text bg-light"><User size={18} /></span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="John Doe"
                    required
                    value={fullName}
                    onChange={(e) => setFullName(e.target.value)}
                  />
                </div>
              </div>

              <div className="mb-3">
                <label className="form-label small fw-semibold">Email Address</label>
                <div className="input-group">
                  <span className="input-group-text bg-light"><Mail size={18} /></span>
                  <input
                    type="email"
                    className="form-control"
                    placeholder="name@example.com"
                    required
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </div>
              </div>

              <div className="mb-3">
                <label className="form-label small fw-semibold">Phone Number</label>
                <div className="input-group">
                  <span className="input-group-text bg-light"><Phone size={18} /></span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="+1 800 555 0199"
                    value={phone}
                    onChange={(e) => setPhone(e.target.value)}
                  />
                </div>
              </div>

              <div className="mb-3">
                <label className="form-label small fw-semibold">Select Account Role</label>
                <div className="input-group">
                  <span className="input-group-text bg-light"><Shield size={18} /></span>
                  <select className="form-select" value={role} onChange={(e) => setRole(e.target.value)}>
                    <option value="ROLE_CUSTOMER">Customer (Book Tickets & View Seats)</option>
                    <option value="ROLE_ORGANIZER">Event Organizer (Create & Manage Events)</option>
                  </select>
                </div>
              </div>

              <div className="mb-4">
                <label className="form-label small fw-semibold">Password</label>
                <div className="input-group">
                  <span className="input-group-text bg-light"><Key size={18} /></span>
                  <input
                    type="password"
                    className="form-control"
                    placeholder="At least 6 characters..."
                    minLength={6}
                    required
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                </div>
              </div>

              <button type="submit" disabled={loading} className="btn btn-primary w-100 btn-lg fw-semibold mb-3">
                {loading ? 'Creating Account...' : 'Register'}
              </button>
            </form>

            <p className="text-center text-muted small mt-3 mb-0">
              Already have an account? <Link to="/login" className="text-primary fw-semibold">Log in</Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};
