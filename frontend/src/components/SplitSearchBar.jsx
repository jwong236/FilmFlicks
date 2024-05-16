import React from 'react';
import { Box, TextField, Button, InputAdornment } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

const SplitSearchBar = ({ title, setTitle, year, setYear, director, setDirector, star, setStar, handleSearch, theme }) => {
    const textFieldStyle = {
        "& .MuiInputBase-input": {
            color: theme.palette.primary.dark,
        },
        "& .MuiInputBase-root": {
            backgroundColor: theme.palette.secondary.light,
        },
    };

    return (
        <Box sx={{
            alignSelf: 'flex-start',
            display: 'flex',
            flexDirection: 'row',
            width: '100%',
            paddingTop: '1rem',
            paddingBottom: '1rem',
            justifyContent: 'center',
            alignItems: 'center',
            gap: '1rem',
        }}>
            <TextField
                placeholder="Title"
                value={title}
                onChange={e => setTitle(e.target.value)}
                InputProps={{
                    startAdornment: (
                        <InputAdornment position="start">
                            <SearchIcon />
                        </InputAdornment>
                    ),
                }}
                sx={{
                    ...textFieldStyle,
                    width: '30vw',
                    minWidth: '8rem'
                }}
            />
            <TextField
                placeholder="Year"
                value={year}
                onChange={e => setYear(e.target.value)}
                InputProps={{
                    startAdornment: (
                        <InputAdornment position="start">
                            <SearchIcon />
                        </InputAdornment>
                    ),
                }}
                sx={{
                    ...textFieldStyle,
                    width: '6vw',
                    minWidth: '6rem'
                }}
            />
            <TextField
                placeholder="Director"
                value={director}
                onChange={e => setDirector(e.target.value)}
                InputProps={{
                    startAdornment: (
                        <InputAdornment position="start">
                            <SearchIcon />
                        </InputAdornment>
                    ),
                }}
                sx={{
                    ...textFieldStyle,
                    width: '15vw',
                    minWidth: '15rem'
                }}
            />
            <TextField
                placeholder="Star"
                value={star}
                onChange={e => setStar(e.target.value)}
                InputProps={{
                    startAdornment: (
                        <InputAdornment position="start">
                            <SearchIcon />
                        </InputAdornment>
                    ),
                }}
                sx={{
                    ...textFieldStyle,
                    width: '15vw',
                    minWidth: '15rem'
                }}
            />
            <Button
                onClick={handleSearch}
                sx={{
                    backgroundColor: '#FF907E',
                    color: 'secondary.light',
                    fontWeight: 'bold',
                    fontSize: '1rem',

                    '&:hover': {
                        backgroundColor: 'primary.main',
                        transform: 'scale(1.05)'
                    }
                }}>
                Search
            </Button>
        </Box>
    );
};

export default SplitSearchBar;
