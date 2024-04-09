import { useState } from 'react'

function MovieList() {
    const [movieData, setMovieData] = useState(null);

    async function fetchMovieData(){
        try{
            const response = await fetch('http://localhost:8080/fabFlix_war/movielist');

            if (!response.ok) {
                console.error('response is not status 200');
            }

            const data = await response.json();

            const contentType = response.headers.get('content-type');

            // Log the content type
            console.log("Content Type: ", contentType);

            console.log(data);
        }catch(error){
            console.error('Error fetching data: ', error);
        }
    }




    return (
        <>
            <div>
                <button onClick={fetchMovieData}>
                    click
                </button>
                <div>

                </div>
            </div>
        </>
    )
}

export default MovieList
