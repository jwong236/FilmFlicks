import React from 'react';
import { Link } from "react-router-dom";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { Button } from "@mui/material";

export default function HomeButton(props) {
    const handleClick = () => {
        console.log('Hello');
    };

    return (
        <Button
            component={Link}
            to='/'
            disableElevation
            startIcon={<ArrowBackIcon />}
            sx={{
                color: '#646CFF',
                position: 'absolute',
                left: 16,
                top: 16,
                "&:hover": {
                    color: '#8086FF',
                    backgroundColor: 'white'
                }
            }}
            onClick={handleClick}
        >
            Home
        </Button>
    );
}
