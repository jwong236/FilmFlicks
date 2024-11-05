// src/pages/LoginPage.jsx
import React from 'react';
import { Box } from "@mui/material";
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import { useLoginState } from "../hooks/login/useLoginState";
import { useLogin } from "../hooks/login/useLogin.jsx"
import LeftPanel from "../components/login/LeftPanel.jsx";
import RightPanel from "../components/login/RightPanel.jsx";

export default function LoginPage() {
    const navigate = useNavigate(); // Initialize navigate

    const { email, setEmail, password, setPassword, result, setResult } = useLoginState();
    const { login } = useLogin(setResult, navigate); // Pass navigate to the hook

    const handleLogin = async () => {
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
            <LeftPanel />
            <RightPanel
                email={email}
                setEmail={setEmail}
                password={password}
                setPassword={setPassword}
                result={result}
                handleLogin={handleLogin}
            />
        </Box>
    );
}
