import React, { useState, useEffect } from 'react';
import { Box, Button, TextField, Typography } from "@mui/material";

const URL = import.meta.env.VITE_BACKEND_URL;

export default function DashboardLoginCard({ setIsLoggedIn, setEmployeeName }) {
    const [credentials, setCredentials] = useState({ email: "", password: "" });
    const [errorMessage, setErrorMessage] = useState("");

    useEffect(() => {
        const fetchSessionData = async () => {
            try {
                const response = await fetch(`${URL}/session/`, {
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
        setErrorMessage("");
    };

    const handleLogin = async () => {
        try {
            const response = await fetch(`${URL}/admin/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify(credentials),
            });

            if (response.ok) {
                const data = await response.json();
                setIsLoggedIn(true);
                setEmployeeName(data.fullname);
            } else {
                const errorData = await response.json();
                setErrorMessage(errorData.message || "Login failed");
                console.error('Login failed:', errorData);
            }
        } catch (error) {
            setErrorMessage("An error occurred during login.");
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
            <Typography variant="h7" sx={{ color: 'secondary.light', marginTop: '1rem' }}>
                Email
            </Typography>
            <TextField
                name="email"
                value={credentials.email}
                onChange={handleInputChange}
                placeholder="Enter your email"
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
                placeholder="Enter your password"
            />
            {errorMessage && (
                <Typography
                    variant="body2"
                    color="error"
                    sx={{ marginTop: '0.5rem' }}
                >
                    {errorMessage}
                </Typography>
            )}
            <Button onClick={handleLogin} variant="contained" sx={{ marginTop: '1rem' }}>
                Login
            </Button>
        </Box>
    );
}
