// src/pages/LoginPage.jsx
import React from 'react';
import { Box, Typography, Button, Checkbox, TextField } from "@mui/material";
import { useNavigate } from "react-router-dom";
import singlepopcorn from '../assets/singlepopcorn.png';
import { useLoginState } from "../hooks/login/useLoginState";
import { useLogin } from "../hooks/login/useLogin.jsx"

export default function LoginPage() {
    const { email, setEmail, password, setPassword, result, setResult } = useLoginState();
    const { login } = useLogin(setResult);
    const navigate = useNavigate();

    // Function to handle login button click
    const handleLogin = async () => {
        // Call the login function from the useLogin hook
        await login(email, password);
    };

    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'row',
                backgroundColor: 'black',
                height: '100vh',
                width: '100vw',
            }}
        >
            <Box
                sx={{
                    display: 'flex',
                    backgroundColor: '#FFFFF4',
                    height: '100vh',
                    flex: '2',
                    backgroundImage: `url(${singlepopcorn})`,
                    '&:hover': {
                        opacity: 0.95,
                    },
                    backgroundRepeat: 'no-repeat',
                    backgroundPosition: 'center',
                }}
            />
            <Box
                sx={{
                    display: 'flex',
                    backgroundColor: 'info.light',
                    height: '100vh',
                    flex: '1',
                }}
            >
                <Box sx={{ display: 'flex', flexDirection: 'column', width: '100%' }}>
                    <Box
                        sx={{
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                            justifyContent: 'center',
                            width: '100%',
                            paddingTop: '5%',
                            paddingBottom: '2%',
                        }}
                    >
                        <Typography variant="h1" component="h1" color="secondary.light" style={{ letterSpacing: 3 }}>
                            FabFlix
                        </Typography>
                        <Typography variant="h3" component="h3" color="secondary.light">
                            Login
                        </Typography>
                    </Box>
                    <Box
                        sx={{
                            width: '100%',
                        }}
                    >
                        <Box>
                            <Typography sx={{ color: 'secondary.light', marginLeft: '5%', paddingTop: '2%' }}>
                                Email Address
                            </Typography>
                            <TextField
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                sx={{ width: '100%', paddingLeft: '5%', paddingRight: '5%' }}
                            />
                        </Box>
                        <Box>
                            <Typography sx={{ color: 'secondary.light', marginLeft: '5%', paddingTop: '2%' }}>
                                Password
                            </Typography>
                            <TextField
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                sx={{ paddingBottom: '2%', width: '100%', paddingLeft: '5%', paddingRight: '5%' }}
                            />
                        </Box>
                        <Box sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center', paddingBottom: '10%' }}>
                            <Checkbox
                                sx={{
                                    paddingLeft: '5%',
                                    color: 'white',
                                }}
                            />
                            <Typography sx={{ color: 'secondary.light' }}>Remember Me</Typography>
                        </Box>
                        <Box
                            sx={{
                                display: 'flex',
                                alignItems: 'center',
                                textAlign: 'center',
                                paddingBottom: '10%',
                                paddingLeft: '5%',
                                fontWeight: '800',
                            }}
                        >
                            <Typography sx={{ color: 'secondary.light' }}>{result}</Typography>
                        </Box>
                    </Box>
                    <Button
                        onClick={handleLogin}
                        sx={{
                            borderRadius: '20px',
                            backgroundColor: '#FF907E',
                            color: 'secondary.light',
                            fontWeight: 'bold',
                            fontSize: '1.2rem',
                            marginLeft: '5%',
                            marginRight: '5%',
                            '&:hover': {
                                backgroundColor: 'primary.main',
                                transform: 'scale(1.05)',
                            },
                        }}
                    >
                        Log In
                    </Button>
                    <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                        <Typography sx={{ color: 'secondary.light', marginLeft: '5%', textDecoration: 'underline', paddingTop: '1%' }}>
                            Forgot Your Password?
                        </Typography>
                    </Box>
                    <Box
                        sx={{
                            display: 'flex',
                            flexDirection: 'column',
                            width: '100%',
                            flexGrow: '1',
                            alignItems: 'center',
                            justifyContent: 'center',
                        }}
                    >
                        <Typography sx={{ color: 'secondary.light' }}>UCI CS122b</Typography>
                        <Typography sx={{ color: 'secondary.light' }}>Created by Andy Phu</Typography>
                        <Typography sx={{ color: 'secondary.light' }}>and Jacob Wong</Typography>
                    </Box>
                </Box>
            </Box>
        </Box>
    );
}
