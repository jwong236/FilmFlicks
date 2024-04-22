import React from 'react';
import { Link } from 'react-router-dom';
import HomeIcon from '@mui/icons-material/Home';
import ListIcon from '@mui/icons-material/List';
import StarIcon from '@mui/icons-material/Star';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import singlepopcorntransparent from '../assets/singlepopcorntransparent.png';
import {Box} from "@mui/material";
import theme from "../theme/index.js";

function Navbar() {
    const navbarStyle = {
        display: 'flex',
        alignItems: 'center',
        background: theme.palette.info.light,
        padding: '10px 20px',
    };

    const navIconsStyle = {
        color: theme.palette.secondary.light,
        textDecoration: 'none',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        marginRight: '1rem',
    };

    return (
        <nav style={navbarStyle}>
            <Box
                sx={{
                    width: 40,
                    height: 40,
                    backgroundImage: `url(${singlepopcorntransparent})`,
                    backgroundSize: 'contain',
                    backgroundRepeat: 'no-repeat',
                    marginRight: '20px'
                }}
            />

            <Link to="/" style={navIconsStyle}>
                <HomeIcon /> Home
            </Link>
            <Link to="/topmovies" style={navIconsStyle}>
                <StarIcon /> Popular
            </Link>
            <Link to="/movielist" style={navIconsStyle}>
                <ListIcon /> List
            </Link>
            <Link to="/checkout" style={navIconsStyle}>
                <ShoppingCartIcon /> Checkout
            </Link>
            {}
        </nav>
    );
}

export default Navbar;
