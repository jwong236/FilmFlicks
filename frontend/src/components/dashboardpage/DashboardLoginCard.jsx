import React, { useState, useEffect } from 'react';
import { Box, Button, TextField, Typography } from "@mui/material";

const URL = import.meta.env.VITE_BACKEND_URL;

export default function DashboardLoginCard({ setIsLoggedIn, setEmployeeName }) {
    const [credentials, setCredentials] = useState({ username: "", password: "" });

    useEffect(() => {
        const fetchSessionData = async () => {
            try {
                const response = await fetch(`${URL}/metadata/session`, {
                    method: 'GET',
                    credentials: 'include',
                });

                if (response.ok) {
                    const data = await response.json();
                    if (data.employee) {
                        setIsLoggedIn(true);
                        setEmployeeName(data.employee.fullName);
                    }
                } else {
                    console.error('Failed to fetch session data');
                }
            } catch (error) {
                console.error('Error fetching session data:', error);
            }
        };

        fetchSessionData();
    }, [setIsLoggedIn, setEmployeeName]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setCredentials((prev) => ({ ...prev, [name]: value }));
    };

    const handleLogin = async () => {
        try {
            const response = await fetch(`${URL}/admin/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                credentials: 'include',
                body: new URLSearchParams(credentials).toString(),
            });

            if (response.ok) {
                const data = await response.json();
                setIsLoggedIn(true);
                setEmployeeName(data.fullname);
            } else {
                console.error('Login failed');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <Box
            sx={{
                display: 'flex',
                height: '40vh',
                width: '50vw',
                flexDirection: 'column',
                backgroundColor: 'info.light',
                borderRadius: '20px',
                padding: '1rem',
            }}
        >
            <Typography
                variant="h4"
                sx={{ color: 'primary.dark', alignSelf: 'center' }}
            >
                Employee Log In
            </Typography>
            <Typography variant="h7" sx={{ color: 'secondary.light' }}>
                Username
            </Typography>
            <TextField
                name="username"
                value={credentials.username}
                onChange={handleInputChange}
            />
            <Typography
                variant="h7"
                sx={{ color: 'secondary.light', marginTop: '1rem' }}
            >
                Password
            </Typography>
            <TextField
                type="password"
                name="password"
                value={credentials.password}
                onChange={handleInputChange}
            />
            <Button onClick={handleLogin} variant="contained" sx={{ marginTop: '1rem' }}>
                Login
            </Button>
        </Box>
    );
}
