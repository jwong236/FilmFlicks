import React, { useState } from 'react';
import { Box, Typography, Button } from "@mui/material";
import Navbar from "../components/common/navbar/Navbar.jsx";
import Background from "../components/components(deprecated)/Background.jsx";
import DashboardLoginCard from "../components/components(deprecated)/dashboard/DashboardLoginCard.jsx";
import DashboardAddMovieCard from "../components/components(deprecated)/dashboard/DashboardAddMovieCard.jsx";

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
