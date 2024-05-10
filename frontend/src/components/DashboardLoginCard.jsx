import React, { useState } from 'react';
import { Box, Button, TextField, Typography } from "@mui/material";

export default function DashboardLoginCard({ handleLogin }) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = () => {
        handleLogin(true);
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
            <Button onClick={handleSubmit} variant="contained" sx={{ marginTop: '1rem'}}>
                Login
            </Button>
        </Box>
    );
}
