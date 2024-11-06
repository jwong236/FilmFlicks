import React, { useState, useEffect } from 'react';
import { Box, TextField, Button, InputAdornment, useTheme, Autocomplete, CircularProgress } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import SearchIcon from '@mui/icons-material/Search';

const URL = import.meta.env.VITE_URL;

const FullTextSearch = ({ sx }) => {
    const [searchQuery, setSearchQuery] = useState("");
    const [inputValue, setInputValue] = useState("");
    const [suggestions, setSuggestions] = useState([]);
    const [loading, setLoading] = useState(false);
    const [highlightedIndex, setHighlightedIndex] = useState(-1);
    const theme = useTheme();
    const navigate = useNavigate();

    const fetchSuggestions = async (query) => {
        console.log("Autocomplete query initiated for: ", query);
        const cachedSuggestions = localStorage.getItem(query);
        if (cachedSuggestions) {
            console.log("Autocomplete using cached results: ", JSON.parse(cachedSuggestions));
            setSuggestions(JSON.parse(cachedSuggestions));
        } else {
            setLoading(true);
            try {
                const response = await fetch(`${URL}/fullTextSearch?title=${encodeURIComponent(query)}&userPage=1&userPageSize=10&sortRule=title_asc_rating_asc`, {
                    method: 'GET',
                    headers: { 'Content-Type': 'application/json' },
                    credentials: 'include'
                });
                const data = await response.json();
                const titles = data.map(item => item.title);
                localStorage.setItem(query, JSON.stringify(titles));
                console.log("Autocomplete using new results: ", JSON.stringify(titles));
                setSuggestions(titles);
            } catch (error) {
                console.error("Error fetching suggestions:", error);
            } finally {
                setLoading(false);
            }
        }
        console.log("Used suggestion list: ", suggestions);
    };

    useEffect(() => {
        if (searchQuery.length >= 3) {
            const delayDebounceFn = setTimeout(() => {
                fetchSuggestions(searchQuery);
            }, 300);

            return () => clearTimeout(delayDebounceFn);
        }
    }, [searchQuery]);

    const handleFullSearch = () => {
        if (!inputValue) {
            console.log('Search field is empty. No action taken.');
            return;
        }
        navigate('/movielist', { state: { title: inputValue, year: null, director: null, star: null } });
    };

    const handleSuggestionSelect = (event, value) => {
        if (value) {
            navigate(`/singlemovie?title=${encodeURIComponent(value)}`);
        }
    };

    const handleKeyDown = (event) => {
        if (suggestions.length > 0) {
            if (event.key === 'ArrowDown') {
                setHighlightedIndex((prevIndex) => {
                    const newIndex = prevIndex + 1 >= suggestions.length ? 0 : prevIndex + 1;
                    setInputValue(suggestions[newIndex]);
                    return newIndex;
                });
            } else if (event.key === 'ArrowUp') {
                setHighlightedIndex((prevIndex) => {
                    const newIndex = prevIndex - 1 < 0 ? suggestions.length - 1 : prevIndex - 1;
                    setInputValue(suggestions[newIndex]);
                    return newIndex;
                });
            }
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
                options={suggestions}
                inputValue={inputValue}
                onInputChange={(event, newInputValue) => {
                    setInputValue(newInputValue);
                    setHighlightedIndex(-1);
                    if (newInputValue.length >= 3) {
                        setSearchQuery(newInputValue);
                    }
                }}
                onChange={handleSuggestionSelect}
                loading={loading}
                filterOptions={(x) => x}
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
                            },
                            onKeyDown: handleKeyDown
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
                renderOption={(props, option, { selected, index }) => (
                    <li
                        {...props}
                        style={{
                            backgroundColor: highlightedIndex === index ? theme.palette.action.hover : 'inherit'
                        }}
                    >
                        {option}
                    </li>
                )}
                ListboxProps={{
                    onMouseDown: (event) => event.preventDefault(),
                }}
            />
            <Button
                variant="contained"
                color="primary"
                onClick={handleFullSearch}
                sx={{
                    fontSize: '1rem',
                    borderRadius: 1
                }}>
                Search
            </Button>
        </Box>
    );
};

export default FullTextSearch;
