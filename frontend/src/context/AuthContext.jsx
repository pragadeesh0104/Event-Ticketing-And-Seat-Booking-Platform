import React, { createContext, useState, useEffect } from 'react';
import API from '../api/axios';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(localStorage.getItem('eventify_token'));
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (token) {
      fetchCurrentUser();
    } else {
      setLoading(false);
    }
  }, [token]);

  const fetchCurrentUser = async () => {
    try {
      const res = await API.get('/auth/me');
      if (res.data.success) {
        setUser(res.data.data);
      }
    } catch (err) {
      logout();
    } finally {
      setLoading(false);
    }
  };

  const login = async (email, password) => {
    const res = await API.post('/auth/login', { email, password });
    if (res.data.success) {
      const authData = res.data.data;
      localStorage.setItem('eventify_token', authData.token);
      setToken(authData.token);
      setUser({
        id: authData.userId,
        fullName: authData.fullName,
        email: authData.email,
        role: authData.role,
        status: authData.status
      });
      return authData;
    }
  };

  const register = async (userData) => {
    const res = await API.post('/auth/register', userData);
    if (res.data.success) {
      const authData = res.data.data;
      localStorage.setItem('eventify_token', authData.token);
      setToken(authData.token);
      setUser({
        id: authData.userId,
        fullName: authData.fullName,
        email: authData.email,
        role: authData.role,
        status: authData.status
      });
      return authData;
    }
  };

  const logout = () => {
    localStorage.removeItem('eventify_token');
    setToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, token, loading, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
