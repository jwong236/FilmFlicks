import {useEffect, useState} from 'react'
import { Link } from "react-router-dom";

function SingleStar() {
    const [starData, setStarData] = useState({
         name: "",
        Year: "N/A",
        AllMoves: []

    });


    useEffect(() => {
        let mounted = true;
        async function fetchMovieData(){
            try{
                const response = await fetch('http://localhost:8080/fabFlix_war/movielist');

                if (!response.ok) {
                    console.error('response is not status 200');
                }

                const data = await response.json();

                if (mounted){
                    setMovieData(data);
                }


            }catch(error){
                console.error('Error fetching data: ', error);
            }
        }

        fetchMovieData();

        return ()=>{
            mounted = false;
        }
    }, []);




    return (
        <>
            <div>
                hello
            </div>
        </>
    );

}

export default SingleStar
