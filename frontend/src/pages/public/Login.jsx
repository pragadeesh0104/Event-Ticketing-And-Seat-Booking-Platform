import React, { useState, useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import { LogIn, Key, Mail, Info } from 'lucide-react';

export const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMsg, setErrorMsg] = useState('');
  const [loading, setLoading] = useState(false);

  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setErrorMsg('');
    try {
      await login(email, password);
      navigate('/events');
    } catch (err) {
      setErrorMsg(err.response?.data?.message || 'Invalid email or password.');
    } finally {
      setLoading(false);
    }
  };

  const setDemoAccount = (demoEmail, demoPass) => {
    setEmail(demoEmail);
    setPassword(demoPass);
  };

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-md-6 col-lg-5">
          <div className="card border-0 shadow-lg rounded-4 p-4 p-md-5">
            <div className="text-center mb-4">
              <div className="bg-primary bg-opacity-10 text-primary p-3 rounded-circle d-inline-block mb-3">
                <LogIn size={32} />
              </div>
              <h3 className="fw-bold">Welcome Back</h3>
              <p className="text-muted small">Log in to manage bookings and reserve tickets</p>
            </div>

            {errorMsg && <div className="alert alert-danger small mb-3">{errorMsg}</div>}

            <form onSubmit={handleSubmit}>
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

              <div className="mb-4">
                <label className="form-label small fw-semibold">Password</label>
                <div className="input-group">
                  <span className="input-group-text bg-light"><Key size={18} /></span>
                  <input
                    type="password"
                    className="form-control"
                    placeholder="••••••••"
                    required
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                </div>
              </div>

              <button type="submit" disabled={loading} className="btn btn-primary w-100 btn-lg fw-semibold mb-3">
                {loading ? 'Authenticating...' : 'Log In'}
              </button>
            </form>

            {/* Quick Demo Logins for Project Reviews */}
            <div className="bg-light p-3 rounded-3 mt-3 border">
              <h6 className="fw-bold small d-flex align-items-center gap-1 mb-2">
                <Info size={14} className="text-primary" /> Quick Demo Accounts (Review Credentials)
              </h6>
              <div className="d-flex flex-wrap gap-2">
                <button onClick={() => setDemoAccount('customer@eventify.com', 'customer123')} className="btn btn-sm btn-outline-secondary">
                  Customer Demo
                </button>
                <button onClick={() => setDemoAccount('organizer@eventify.com', 'organizer123')} className="btn btn-sm btn-outline-info">
                  Organizer Demo
                </button>
                <button onClick={() => setDemoAccount('admin@eventify.com', 'admin123')} className="btn btn-sm btn-outline-warning">
                  Admin Demo
                </button>
              </div>
            </div>

            <p className="text-center text-muted small mt-4 mb-0">
              Don't have an account? <Link to="/register" className="text-primary fw-semibold">Register here</Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};
