// src/components/login/RightPanel.jsx
import React from 'react';
import { Box, Button, Checkbox, Typography } from '@mui/material';
import InputField from './InputField';
import LoginHeader from './LoginHeader';
import LoginFooter from './LoginFooter';
import { useTheme } from '@mui/material/styles';

export default function RightPanel({ email, setEmail, password, setPassword, result, handleLogin }) {
    const theme = useTheme();
    return (
        <Box
            sx={{
                display: 'flex',
                backgroundColor: theme.palette.info.light,
                height: '100vh',
                flex: '1',
            }}
        >
            <Box sx={{ display: 'flex', flexDirection: 'column', width: '100%' }}>
                <LoginHeader />
                <Box sx={{ width: '100%' }}>
                    <InputField label="Email Address" value={email} onChange={(e) => setEmail(e.target.value)} />
                    <InputField
                        label="Password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <Box sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center', paddingBottom: '10%' }}>
                        <Checkbox disableRipple
                            sx={{
                                '&:hover': {
                                    backgroundColor: 'transparent',
                                },
                                paddingLeft: '5%',
                                color: theme.palette.secondary.main,
                            }}
                        />
                        <Typography sx={{ color: theme.palette.secondary.light }}>Remember Me</Typography>
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
                        <Typography sx={{ color: theme.palette.secondary.light }}>{result}</Typography>
                    </Box>
                </Box>

                <Button
                    variant="contained"
                    color="primary"
                    onClick={handleLogin}
                    sx={{
                        marginLeft: '5%',
                        marginRight: '5%',
                    }}
                >
                    Log In
                </Button>

                <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                    <Typography
                        sx={{
                            color: theme.palette.secondary.light,
                            marginLeft: '5%',
                            textDecoration: 'underline',
                            paddingTop: '1%',
                        }}
                    >
                        Forgot Your Password?
                    </Typography>
                </Box>
                <LoginFooter />
            </Box>
        </Box>
    );
}
