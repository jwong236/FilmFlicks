import React, { useEffect, useState } from 'react';
import {
    Box, Button, Chip, InputAdornment, TextField, Typography, useTheme, Select, MenuItem, FormControl, InputLabel
} from "@mui/material";
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import Background from '../components/Background.jsx';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import SearchIcon from "@mui/icons-material/Search.js";
import MovieListTable from "../components/MovieListTable.jsx";

const HOST = import.meta.env.VITE_HOST;
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

    const handleSearch = () => {
        if (!title && !year && !director && !star) {
            console.log('All search fields are empty. No action taken.');
            return;
        }
        navigate('/movielist', { state: { title, year, director, star } });
    };

    const handlePageDropDown = (event) => {
        setPageSize(event.target.value);
        console.log(pageSize);
    };

    const handleSortDropDown = (event) => {
        setSortRule(event.target.value);
        console.log(sortRule);
    };

    useEffect(() => {

        const fetchData = async () => {
            let endpoint = `http://${HOST}:8080/fabFlix/`;
            let endpointCategory = "";
            let params = { page, pageSize, sortRule };
            let prevSessionFlag = false;

            if (location.state === null){
                console.log("empty search -> use prev session");
                prevSessionFlag = true;
            }

            if(!prevSessionFlag){
                console.log("dont use prev session, update it ");
                if (location.state.title || location.state.year || location.state.director || location.state.star) {
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
                let tempParams = {...params, "endpoint" : endpointCategory};
                console.log(tempParams);
                const prevResponse = await axios.get(`http://${HOST}:8080/fabFlix/previousGetter`,{
                    withCredentials: true,
                    params: tempParams
                });
                // const prevResponse = await fetch(`http://${HOST}:8080/fabFlix/previousGetter`,{
                //     credentials: 'include',
                //     params: tempParams
                // });

                //if it is 201 or 100 then use the newly updated params
                if ((prevResponse.status === 201 )|| (prevResponse.status === 100)){
                    console.log("using the current params");
                    console.log("endpoint " + endpoint);
                    console.log("params ", params);
                    const response = await axios.get(endpoint, { params });
                    console.log("Received:", response.data);
                    setMovies(response.data);
                }else if (prevResponse.status === 200){
                    console.log("going to use prev and checking for the prev data");
                    //use the previous given to by prevResponse
                    // const prevData = await prevResponse.text;
                    const prevData = await prevResponse.data;
                    console.log("prev data ", prevData);

                    const prevEndpoint = Object.keys(prevData)
                    console.log("prev data endpoint " + prevEndpoint[0]);

                    const prevParams = {};
                    if (prevEndpoint[0] === "search"){
                        console.log("end point is search");
                        for (const key in prevData.search) {
                            prevParams[key] = prevData.search[key];
                        }
                    }
                    // else if (prevEndpoint[0] === "genre"){
                    //     console.log("end point is browse/genre");
                    //     for (const key in prevData.genre) {
                    //         prevParams[key] = prevData.search[key];
                    //     }
                    // }


                    console.log("prev params ", prevParams);

                    if (prevData && prevEndpoint && prevParams) {
                        //generating the old end point with the params to get prev data
                        const endpointUrl = `http://${HOST}:8080/fabFlix/${prevEndpoint}`;
                        console.log("endpoint url " + endpointUrl);


                        try {
                            const response = await axios.get(endpointUrl,{
                                params: prevParams
                            });
                            console.log("Received:", response.data);

                            setMovies(response.data);
                        } catch (error) {
                            console.error("Failed to fetch movie data:", error);
                        }
                    } else {
                        console.log("Previous data not available or invalid");
                        const oldResponse = await axios.get(endpoint, { params });
                        setMovies(oldResponse.data);
                    }
                }else{
                    console.error("error on server side : previousGetter");
                }
            }
            catch (error) {
                console.error("Failed to fetch data:", error);
            }
        };
        fetchData();
    }, [location.state, page, pageSize, sortRule]);

    const theme = useTheme();
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
            display: 'flex',
            height: '100vh',
            width: '100vw',
            flexDirection: 'column'
        }}>
            <Navbar />
            <Background sx={{ justifyContent: 'center', alignItems: 'center'}}>
                <Box sx={{
                    display: 'flex',
                    backgroundColor: 'info.light',
                    width: '95vw',
                    height: '85vh',
                    borderRadius: '20px',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}>
                    <Box sx={{
                        alignSelf: 'flex-start',
                        display: 'flex',
                        flexDirection: 'row',
                        width: '100%',
                        paddingTop: '1rem',
                        paddingBottom: '.5rem',
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
                                backgroundColor: 'primary.main',
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
                    <Box sx={{
                        display: 'flex',
                        width: '95%',
                        alignItems: 'center',
                        justifyContent: 'space-between',
                        paddingBottom: '.5rem'
                    }}>
                        <Typography variant='h4' component='h4' color='primary.dark' sx={{
                            fontWeight: 'bold',
                        }}>
                            Results
                        </Typography>
                        <Box sx={{
                            display: 'flex',
                            alignItems: 'center',
                            gap: 1
                        }}>
                            <Typography sx={{ color: 'primary.dark' }}>
                                Movies Per Page:
                            </Typography>
                            <FormControl variant="outlined" size="small">
                                <Select
                                    value={pageSize}
                                    onChange={handlePageDropDown}
                                    sx={{ minWidth: '5rem' }}
                                >
                                    <MenuItem value={10}>10</MenuItem>
                                    <MenuItem value={25}>25</MenuItem>
                                    <MenuItem value={50}>50</MenuItem>
                                    <MenuItem value={100}>100</MenuItem>
                                </Select>
                            </FormControl>
                            <Typography sx={{ color: 'primary.dark' }}>
                                Sort By:
                            </Typography>
                            <FormControl variant="outlined" size="small">
                                <Select
                                    value={sortRule}
                                    onChange={handleSortDropDown}
                                    sx={{ minWidth: '5rem' }}
                                >
                                    <MenuItem value={"title_asc_rating_asc"}>Title ↑ / Rating ↑</MenuItem>
                                    <MenuItem value={"title_asc_rating_desc"}>Title ↑ / Rating ↓</MenuItem>
                                    <MenuItem value={"title_desc_rating_asc"}>Title ↓ / Rating ↑</MenuItem>
                                    <MenuItem value={"title_desc_rating_desc"}>Title ↓ / Rating ↓</MenuItem>
                                    <MenuItem value={"rating_asc_title_asc"}>Rating ↑ / Title ↑</MenuItem>
                                    <MenuItem value={"rating_asc_title_desc"}>Rating ↑ / Title ↓</MenuItem>
                                    <MenuItem value={"rating_desc_title_asc"}>Rating ↓ / Title ↑</MenuItem>
                                    <MenuItem value={"rating_desc_title_desc"}>Rating ↓ / Title ↓</MenuItem>
                                </Select>
                            </FormControl>

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
                        <MovieListTable data = {movies} />
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
                        }}>
                            Previous
                        </Button>
                        <Button sx={{
                            backgroundColor: 'primary.main',
                            color: 'secondary.light',
                            height: '4vh',
                            '&:hover': {
                                backgroundColor: 'primary.dark'
                            }
                        }}>
                            Next
                        </Button>
                    </Box>
                </Box>
            </Background>
        </Box>
    );
}