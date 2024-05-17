import React, { useState, useEffect } from 'react';
import { Box, TextField, Button, InputAdornment, useTheme, Autocomplete, CircularProgress } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import SearchIcon from '@mui/icons-material/Search';
const URL = import.meta.env.VITE_URL;

const FullTextSearch = ({ sx }) => {
    const [searchQuery, setSearchQuery] = useState("");
    const [suggestions, setSuggestions] = useState([]);
    const [loading, setLoading] = useState(false);
    const theme = useTheme();
    const navigate = useNavigate();

    const fetchSuggestions = async (query) => {
        console.log("Autocomplete search initiated");
        const cachedSuggestions = localStorage.getItem(query);
        if (cachedSuggestions) {
            console.log("Using cached results");
            setSuggestions(JSON.parse(cachedSuggestions));
        } else {
            console.log("Sending ajax request to the server");
            setLoading(true);
            try {
                const response = await fetch(`${URL}/fullTextSearch?title=${encodeURIComponent(query)}&userPage=1&userPageSize=10&sortRule=title_asc_rating_asc`, {
                    method: 'GET',
                    headers: { 'Content-Type': 'application/json' },
                    credentials: 'include'
                });
                const data = await response.json();
                console.log("Received suggestions:", data);
                localStorage.setItem(query, JSON.stringify(data));
                setSuggestions(data);
            } catch (error) {
                console.error("Error fetching suggestions:", error);
            } finally {
                setLoading(false);
            }
        }
    };

    useEffect(() => {
        const delayDebounceFn = setTimeout(() => {
            if (searchQuery.length >= 3) {
                fetchSuggestions(searchQuery);
            }
        }, 300);

        return () => clearTimeout(delayDebounceFn);
    }, [searchQuery]);

    const handleFullSearch = () => {
        if (!searchQuery) {
            console.log('Search field is empty. No action taken.');
            return;
        }
        navigate('/movielist', { state: { title: searchQuery, year: null, director: null, star: null } });
    };

    const handleSuggestionSelect = (event, value) => {
        if (value) {
            navigate(`/singlemovie?title=${encodeURIComponent(value)}`);
        }
    };

    return (
        <Box sx={{
            ...sx,
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'center',
            gap: '1rem',
        }}>
            <Autocomplete
                freeSolo
                options={suggestions.map(suggestion => suggestion.title)}
                onInputChange={(event, newInputValue) => setSearchQuery(newInputValue)}
                onChange={handleSuggestionSelect}
                loading={loading}

                renderInput={(params) => (
                    <TextField
                        {...params}
                        placeholder="Search for movies..."
                        InputProps={{
                            ...params.InputProps,
                            startAdornment: (
                                <InputAdornment position="start">
                                    <SearchIcon />
                                </InputAdornment>
                            ),
                            endAdornment: (
                                <>
                                    {loading ? <CircularProgress color="inherit" size={20} /> : null}
                                    {params.InputProps.endAdornment}
                                </>
                            ),
                            sx: {
                                alignItems: 'center',
                            }
                        }}
                        sx={{
                            width: '40vw',
                            minWidth: '10rem',
                            '& .MuiInputBase-root': {
                                backgroundColor: theme.palette.secondary.light,
                                paddingBottom: '1.2rem'
                            },
                            '& .MuiInputBase-input': {
                                color: theme.palette.primary.dark,
                            },
                        }}

                    />
                )}
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
