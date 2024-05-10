import React, { useState } from 'react';
import { Box, Typography, Button } from "@mui/material";
import Navbar from "../components/Navbar.jsx";
import Background from "../components/Background.jsx";
import DashboardLoginCard from "../components/DashboardLoginCard.jsx";
import DashboardAddMovieCard from "../components/DashboardAddMovieCard.jsx";

export default function Dashboard(){

    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [employeeName, setEmployeeName] = useState();


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
                    <DashboardAddMovieCard employeeName={employeeName}/>
                ) : <DashboardLoginCard setIsLoggedIn={setIsLoggedIn} setEmployeeName={setEmployeeName} />
                }
            </Background>
        </Box>
    );
}
