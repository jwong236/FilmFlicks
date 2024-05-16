import React, { useEffect, useState } from 'react';
import { Box, Button, Typography, Snackbar, useTheme } from "@mui/material";
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import Background from '../components/Background.jsx';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import MovieListTable from "../components/MovieListTable.jsx";
import SplitSearchBar from "../components/SplitSearchBar.jsx";
import MoviesPerPageDropdown from "../components/MoviesPerPageDropdown.jsx";
import SortByDropdown from "../components/SortByDropdown.jsx";
import AdvancedSearchBar from "../components/AdvancedSearchBar.jsx";

const URL = import.meta.env.VITE_URL;

export default function MovieList() {
    const [title, setTitle] = useState("");
    const [year, setYear] = useState("");
    const [director, setDirector] = useState("");
    const [star, setStar] = useState("");
    const [movies, setMovies] = useState([]);
    const [pageSize, setPageSize] = useState(10);
    const [page, setPage] = useState(1);
    const [sortRule, setSortRule] = useState("title_asc_rating_asc");
    const location = useLocation();
    const navigate = useNavigate();
    const [openSnackbar, setOpenSnackbar] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState("");
    const [searchQuery, setSearchQuery] = useState("");

    const theme = useTheme();
    function endpointShaper(endpoint) {
        switch (endpoint) {
            case "browsegenre":
                return "browse/genre";
            case "browsecharacter":
                return "browse/character";
            case "fullTextSearch":
                return "fullTextSearch";
            default:
                return "search";
        }
    }
    const handleAdvancedSearch = () => {
        if (!searchQuery) {
            console.log('Search field is empty. No action taken.');
            return;
        }
        navigate('/movielist', { state: { title: searchQuery } });
    };
    const addToShoppingCart = async (movie) => {
        try {
            console.log("The movie that was added is: " + movie.title);
            const response = await fetch(`${URL}/add`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify({ "movieTitle": movie.title })
            });

            if (response.status === 401) {
                console.log("REDIRECTION FROM MOVIE LIST");
                navigate('/login');
            } else {
                shoppingCart();
                totalPrice();

                setSnackbarMessage("Movie added successfully!");
                setOpenSnackbar(true);
                setTimeout(() => {
                    setOpenSnackbar(false);
                }, 3000);
            }
        } catch (error) {
            console.error('Error adding to cart: ', error);
            setSnackbarMessage("Failed to add movie to cart.");
            setOpenSnackbar(true);
            setTimeout(() => {
                setOpenSnackbar(false);
            }, 3000);
        }
    };

    const handleSplitSearch = () => {
        if (!title && !year && !director && !star) {
            console.log('All search fields are empty. No action taken.');
            return;
        }
        navigate('/movielist', { state: { title, year, director, star } });
    };

    const handleNextClick = () => {
        if (movies.length >= pageSize) {
            setPage(prevPage => prevPage + 1);
        }
    };

    const handlePrevClick = () => {
        setPage(prevPage => prevPage > 1 ? prevPage - 1 : 1);
    };

    useEffect(() => {
        const fetchData = async () => {
            let endpoint = `${URL}/`;
            let endpointCategory = "";
            let params = { page, pageSize, sortRule };
            let prevSessionFlag = location.state === null;

            if (!prevSessionFlag) {
                if (location.state.title && !location.state.year && !location.state.director && !location.state.star) {
                    endpoint += 'fullTextSearch';
                    endpointCategory = "fullTextSearch";
                    params = {...params, title: location.state.title};
                }
                else if (location.state.title || location.state.year || location.state.director || location.state.star) {
                    endpoint += 'search';
                    endpointCategory = "search";
                    params = { ...params, ...location.state };
                } else if (location.state.genre) {
                    endpoint += 'browse/genre';
                    endpointCategory = "browse/genre";
                    params = { ...params, genre: location.state.genre };
                } else if (location.state.character) {
                    endpoint += 'browse/character';
                    endpointCategory = "browse/character";
                    params = { ...params, character: location.state.character };
                }
            }

            try {
                let tempParams = { ...params, "endpoint": endpointCategory };

                const prevResponse = await axios.get(`${URL}/previousGetter`, {
                    withCredentials: true,
                    params: tempParams
                });

                console.log("prevResponse:", prevResponse);

                let newParams;
                if (prevResponse.status === 201 || prevResponse.status === 200) {
                    const prevData = prevResponse.data;
                    const prevEndpoint = Object.keys(prevData)[0];
                    newParams = prevData[prevEndpoint];

                    setSortRule(newParams.sortRule);
                    setPageSize(newParams.pageSize);

                    endpoint = `${URL}/${endpointShaper(prevEndpoint)}`;
                } else {
                    throw new Error("Unexpected response status from previousGetter");
                }

                if ((endpointCategory === "browsegenre" && !newParams.genre) || (endpointCategory === "browsecharacter" && !newParams.character)) {
                    throw new Error(`${endpointCategory === "browsegenre" ? "Genre" : "Character"} parameter is required.`);
                }

                const response = await axios.get(endpoint, {
                    params: newParams,
                    withCredentials: true
                });

                setMovies(response.data);
            } catch (error) {
                if (error.response && error.response.status === 401) {
                    navigate('/login');
                } else {
                    console.error("Error fetching movie list:", error);
                }
            }
        };

        fetchData();
    }, [location.state, page, pageSize, sortRule]);

    return (
        <Box sx={{
            display: 'flex',
            height: '100vh',
            width: '100vw',
            flexDirection: 'column'
        }}>
            <Navbar />
            <Background sx={{ justifyContent: 'center', alignItems: 'center' }}>
                <Box sx={{
                    display: 'flex',
                    backgroundColor: 'info.light',
                    width: '95vw',
                    height: '85vh',
                    borderRadius: '20px',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}>
                    <Typography variant="h4" component='h4' color="primary.dark">
                        Full Search
                    </Typography>
                    <AdvancedSearchBar
                        searchQuery={searchQuery}
                        setSearchQuery={setSearchQuery}
                        handleSearch={handleAdvancedSearch}
                        theme={theme}
                    />
                    <Typography variant="h4" component='h4' color="primary.dark">
                        Search by Movie Traits
                    </Typography>
                    <SplitSearchBar
                        title={title}
                        setTitle={setTitle}
                        year={year}
                        setYear={setYear}
                        director={director}
                        setDirector={setDirector}
                        star={star}
                        setStar={setStar}
                        handleSearch={handleSplitSearch}
                        theme={theme}
                    />
                    <Box sx={{
                        display: 'flex',
                        width: '95%',
                        alignItems: 'center',
                        justifyContent: 'space-between',
                        paddingBottom: '.5rem'
                    }}>
                        <Typography variant='h4' component='h4' color='primary.dark' sx={{ fontWeight: 'bold' }}>
                            Results
                        </Typography>
                        <Box sx={{
                            display: 'flex',
                            alignItems: 'center',
                            gap: 1
                        }}>
                            <MoviesPerPageDropdown pageSize={pageSize} setPageSize={setPageSize} />
                            <SortByDropdown sortRule={sortRule} setSortRule={setSortRule} />
                        </Box>
                    </Box>
                    <Box sx={{
                        display: 'flex',
                        flexGrow: 1,
                        width: '95%',
                        overflowY: 'auto',
                        overflowX: 'auto',
                        flexDirection: 'row'
                    }}>
                        <MovieListTable data={movies} handleAdd={addToShoppingCart} />
                        <Snackbar
                            open={openSnackbar}
                            message={snackbarMessage}
                            autoHideDuration={3000}
                            onClose={() => setOpenSnackbar(false)}
                        />
                    </Box>
                    <Box sx={{
                        display: 'flex',
                        paddingBottom: '.5rem',
                        paddingTop: '.5rem',
                        flexDirection: 'row',
                        alignItems: 'center',
                        gap: '2rem'
                    }}>
                        <Button sx={{
                            backgroundColor: 'primary.main',
                            color: 'secondary.light',
                            height: '4vh',
                            '&:hover': {
                                backgroundColor: 'primary.dark'
                            }
                        }}
                                onClick={handlePrevClick}
                        >
                            Previous
                        </Button>
                        <Button sx={{
                            backgroundColor: 'primary.main',
                            color: 'secondary.light',
                            height: '4vh',
                            '&:hover': {
                                backgroundColor: 'primary.dark'
                            }
                        }}
                                onClick={handleNextClick}
                        >
                            Next
                        </Button>
                    </Box>
                </Box>
            </Background>
        </Box>
    );
}
