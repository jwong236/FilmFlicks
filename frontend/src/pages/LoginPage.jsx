import React from 'react';
import { Box } from "@mui/material";
import { useLoginPageHooks } from '../hooks/useLoginPageHooks.jsx';
import LeftPanel from "../components/loginpage/LeftPanel.jsx";
import RightPanel from "../components/loginpage/RightPanel.jsx";

export default function LoginPage() {
    const {
        email, setEmail,
        password, setPassword,
        result, handleLogin,
    } = useLoginPageHooks();

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
