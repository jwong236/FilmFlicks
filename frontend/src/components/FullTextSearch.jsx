import React, { useState } from 'react';
import { Box, TextField, Button, InputAdornment, useTheme } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import SearchIcon from '@mui/icons-material/Search';

const FullTextSearch = ({ sx }) => {
    const [searchQuery, setSearchQuery] = useState("");
    const theme = useTheme();
    const navigate = useNavigate();

    const textFieldStyle = {
        "& .MuiInputBase-input": {
            color: theme.palette.primary.dark,
        },
        "& .MuiInputBase-root": {
            backgroundColor: theme.palette.secondary.light,
        },
    };

    const handleFullSearch = () => {
        if (!searchQuery) {
            console.log('Search field is empty. No action taken.');
            return;
        }
        navigate('/movielist', { state: { title: searchQuery, year: null, director: null, star: null } });
    };


    return (
        <Box sx={{
            ...sx,
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'center',
            gap: '1rem',
        }}>
            <TextField
                placeholder="Search for movies..."
                value={searchQuery}
                onChange={e => setSearchQuery(e.target.value)}
                InputProps={{
                    startAdornment: (
                        <InputAdornment position="start">
                            <SearchIcon />
                        </InputAdornment>
                    ),
                }}
                sx={{
                    ...textFieldStyle,
                    width: '40vw',
                    minWidth: '10rem'
                }}
            />
            <Button
                onClick={handleFullSearch}
                sx={{
                    backgroundColor: '#FF907E',
                    color: theme.palette.secondary.light,
                    fontWeight: 'bold',
                    fontSize: '1rem',
                    '&:hover': {
                        backgroundColor: theme.palette.primary.main,
                        transform: 'scale(1.05)'
                    }
                }}>
                Search
            </Button>
        </Box>
    );
};

export default FullTextSearch;
