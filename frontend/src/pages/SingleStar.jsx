import {useEffect, useState} from 'react'
import {useLocation} from "react-router-dom";

function SingleStar() {
    const [starData, setStarData] = useState({
         name: "",
        Year: "N/A",
        AllMovies: []

    });

    const location = useLocation();
    const search_params = new URLSearchParams(location.search);
    const name = search_params.get('name');
    //console.log(name);

    useEffect(() => {
        let mounted = true;
        async function fetchStarData(){
            try{
                const response = await fetch(`http://localhost:8080/fabFlix_war/singlestar?name=${encodeURIComponent(name)}`);

                console.log(response);
                if (!response.ok) {
                    console.error('response is not status 200');
                }


                const data = await response.json();

                if (mounted){
                    setStarData(data);
                }

                console.log(data);

            }catch(error){
                console.error('Error fetching data: ', error);
            }
        }

        fetchStarData();

        return ()=>{
            mounted = false;
        }
    }, []);




    return (
        <>
            <div>
                {starData.name}
            </div>
        </>
    );

}

export default SingleStar
