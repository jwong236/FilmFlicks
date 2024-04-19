import React, {useEffect, useState} from 'react'
import {Link, useLocation, useNavigate} from "react-router-dom";
import HomeButton from "../components/HomeButton.jsx";
import StarCard from "../components/StarCard.jsx";
import {Box}  from '@mui/material'
import popcorn from "../assets/popcorn.png";

const HOST = import.meta.env.VITE_HOST;

function SingleStar() {
    const [starData, setStarData] = useState({
        name: "",
        birthYear: "N/A",
        movieTitles: []

    });

    const location = useLocation();
    const search_params = new URLSearchParams(location.search);
    const name = search_params.get('name');
    //console.log(name);
    const navigate = useNavigate();

    useEffect(() => {
        let mounted = true;
        async function fetchStarData(){
            try{
                const response = await fetch(`http://${HOST}:8080/fabFlix/singlestar?name=${encodeURIComponent(name)}`,{
                    credentials: 'include'
                });
                if (!response.ok) {
                    console.error('response is not status 200');
                }

                console.log("DATA AS TEXT IN SINGLE STAR LIST " + response.text);

                if (response.status === 401){
                    console.log("REDIRECTION FROM MOVIE LIST");
                    navigate('/login')
                }else{
                    const jsonData = await response.json();
                    console.log("no need to login");
                    if (mounted){
                        setStarData(jsonData[0]);
                    }
                }
            }catch(error){
                console.error('Error fetching data: ', error);
            }
        }

        fetchStarData();

        return ()=>{
            mounted = false;
        }
    }, []);
    const birthYear = starData.birthYear === null ? "N/A" : starData.birthYear;
    let movieArr = [];
    if (starData.movieTitles.includes(',')){
        movieArr = starData.movieTitles.split(', ');
        console.log(movieArr);
    }
    else{
        movieArr.push(starData.movieTitles);
        console.log("one movie title");
    }
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
                <StarCard
                    name = {starData.name}
                    birthYear = {birthYear}
                    movieTitles = {movieArr}
                />
            </Box>
        </Box>
    );
}

export default SingleStar
