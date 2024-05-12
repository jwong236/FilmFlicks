import React, { useState, useRef } from 'react';
import { Box, Button, TextField, Typography } from "@mui/material";
import ReCAPTCHA from 'react-google-recaptcha';

const URL = import.meta.env.VITE_URL;
const SITE_KEY = import.meta.env.VITE_SITE_KEY;  // Ensure you have this in your environment variables

export default function DashboardLoginCard({ setIsLoggedIn, setEmployeeName }) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const recaptchaRef = useRef(null);

    const handleLogin = () => {
        const recaptchaValue = recaptchaRef.current.getValue();
        if (!recaptchaValue) {
            console.error('Please complete the reCAPTCHA');
            return;
        }

        fetch(`${URL}/employeeLogin`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include',
            body: JSON.stringify({
                email: email,
                password: password,
                recaptchaValue: recaptchaValue
            })
        })
            .then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        setIsLoggedIn(true);
                        setEmployeeName(data.fullname);
                        recaptchaRef.current.reset();
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
            <ReCAPTCHA
                ref={recaptchaRef}
                sitekey={SITE_KEY}
                style={{ margin: '20px 0' }}
            />
            <Button onClick={handleLogin} variant="contained" sx={{ marginTop: '1rem'}}>
                Login
            </Button>
        </Box>
    );
}
