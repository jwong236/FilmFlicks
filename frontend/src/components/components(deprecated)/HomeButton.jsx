import React from 'react';
import { Link } from "react-router-dom";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { Button } from "@mui/material";

export default function HomeButton(props) {
    const handleClick = () => {
        console.log('Hello');
    };

    return (
        <Button variant="contained"
            component={Link}
            to='/'
            disableElevation
            startIcon={<ArrowBackIcon />}
            sx={{
                position: 'absolute',
                left: 16,
                top: 16,
                color: 'secondary.light',
                backgroundColor: 'info.light',
                '&:hover': {
                    backgroundColor: 'info.main',
                }
            }}
            onClick={handleClick}
        >
            Home
        </Button>
    );
}
