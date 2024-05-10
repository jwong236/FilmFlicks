import React, { useState } from 'react';
import { Box, Typography, Button } from "@mui/material";
import Navbar from "../components/Navbar.jsx";
import Background from "../components/Background.jsx";
import DashboardLoginCard from "../components/DashboardLoginCard.jsx";
import DashboardAddMovieCard from "../components/DashboardAddMovieCard.jsx";

export default function Dashboard(){

    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const handleLogin = () => {
        setIsLoggedIn(true);
    };

    return(
        <Box sx={{
            display: 'flex',
            height: '100vh',
            width: '100vw',
            flexDirection: 'column'
        }}>
            <Navbar/>
            <Background sx={{alignItems: 'center', justifyContent: 'center'}}>
                {isLoggedIn ? (
                    <DashboardAddMovieCard />
                ) : <DashboardLoginCard handleLogin={handleLogin} />
                }
            </Background>
        </Box>
    );
}
