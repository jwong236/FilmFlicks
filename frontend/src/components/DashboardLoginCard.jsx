import React, { useState } from 'react';
import { Box, Button, TextField, Typography } from "@mui/material";

const URL = import.meta.env.VITE_URL;

export default function DashboardLoginCard({ setIsLoggedIn, setEmployeeName }) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = () => {
        console.log("Sending login request with email:", email, "and password:", password);

        fetch(`${URL}/employeeLogin`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include',
            body: JSON.stringify({
                email: email,
                password: password
            })
        })
            .then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        setIsLoggedIn(true);
                        setEmployeeName(data.fullname);
                        console.log(data.fullname);
                    });
                } else {
                    console.error('Login failed');
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    return (
        <Box sx={{
            display: 'flex',
            height: '40vh',
            width: '50vw',
            flexDirection: 'column',
            backgroundColor: 'info.light',
            borderRadius: '20px',
            padding: '1rem'
        }}>
            <Typography variant='h4' sx={{ color: 'primary.dark', alignSelf: 'center' }}>
                Employee Log In
            </Typography>
            <Typography variant='h7' sx={{ color: 'secondary.light' }}>
                Email
            </Typography>
            <TextField
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
            <Typography variant='h7' sx={{ color: 'secondary.light', marginTop: '1rem'}}>
                Password
            </Typography>
            <TextField
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <Button onClick={handleLogin} variant="contained" sx={{ marginTop: '1rem'}}>
                Login
            </Button>
        </Box>
    );
}
