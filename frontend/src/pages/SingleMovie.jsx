import React, {useEffect, useState} from 'react'
import {Link, useLocation, useNavigate} from "react-router-dom";
import {Box, Container} from "@mui/material";
import MovieCard from "../components/MovieCard.jsx";
import HomeButton from "../components/HomeButton.jsx";
import popcorn from '../assets/popcorn.png'

const HOST = import.meta.env.VITE_HOST;

export default function SingleMovie() {
    const [movieData, setMovieData] = useState({
        title: "default title",
        year: "default year",
        director: "default director",
        genres: ["genre1"],
        stars: ["star1"]

    });

    const location = useLocation();
    const search_params = new URLSearchParams(location.search);
    const title = search_params.get('title');
    const navigate = useNavigate();

    useEffect(() => {
        let mounted = true;
        async function fetchMovieData(){
            try{
                const response = await fetch(`http://${HOST}:8080/fabFlix/singlemovie?title=${encodeURIComponent(title)}`,{
                    credentials: 'include'
                });

                if (!response.ok) {
                    console.error('response is not status 200');
                }

                console.log("DATA AS TEXT IN MOVIE LIST " + response.text);

                if (response.status === 401){
                    console.log("REDIRECTION FROM MOVIE LIST");
                    navigate('/login')
                }else{
                    const jsonData = await response.json();
                    console.log("no need to login");
                    if (mounted){
                        console.log(jsonData);
                        setMovieData(jsonData[0]);
                    }
                }

            }catch(error){
                console.error('Error fetching data: ', error);
            }
        }

        fetchMovieData();

        return ()=>{
            mounted = false;
        }
    }, [title]);

    return (
            <Box sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                width: '100%',
                height: '100vh',
                backgroundColor: '#f1f1f1',
                padding: 3,
                backgroundImage: `url(${popcorn})`,
                backgroundSize: 'cover',
                backgroundRepeat: 'no-repeat',
                }}>
                <HomeButton/>
                <Box sx={{width: '30%'}}>
                    <MovieCard title={movieData.title} year={movieData.year} director={movieData.director} rating={movieData.rating} stars={movieData.stars} genres={movieData.genres} link={false}/>
                </Box>
            </Box>
    );

}
