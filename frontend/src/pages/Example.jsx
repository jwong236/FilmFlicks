import {useEffect, useState} from 'react'
import { Link } from "react-router-dom";

// Jacob's Notes: .jsx contains a function which is used as a React component
// React component can take on multiple forms. It can be buttons, dropdowns, but in this case it's a webpage
// If the component accepts arguments it can't be changed and is referred to as a 'prop' or a 'property"
// useStates store data pertaining to the component. useState always returns an array of 2 fields, with the first being a variable (a state) and the second being a method to modify the variable
// For example if this component is a button, the first field can be count and the second field can be setCount. Then to modify the state you call the function
function Example() {
    const [directorData, setDirectorData] = useState(null);

    useEffect(() => {
        let mounted = true;
        async function fetchDirectorData(){
            try{
                const response = await fetch('http://localhost:8080/fabFlix_war/example');
                if (!response.ok) {
                    console.error('response is not status 200');
                }
                const data = await response.json();
                if (mounted){
                    setDirectorData(data);
                }
            } catch(error){
                console.error('Error fetching data: ', error);
            }
        }
        fetchDirectorData();

        return ()=>{
            mounted = false;
        }
    }, []);




    return (
        <>
            <h1 className="text-2xl font-bold mb-4">RANDOM LIST OF DIRECTORS</h1>
            {directorData ? (
                <table className="border border-collapse border-black">
                    <thead>
                    <tr className="bg-blue-600 text-white">
                        <th className="border border-black px-4 py-2">Director</th>
                    </tr>
                    </thead>
                    <tbody>
                        {directorData.map((elem, index) => {
                            const bgColor = index % 2 === 0 ? "bg-gray-100" : "bg-white";
                            const directorArray = elem.director.split(', ');
                            const firstDirector = directorArray[0];
                            const secondDirector = directorArray[1];
                            const thirdDirector = directorArray[2];
                            console.log(firstDirector);
                            console.log(secondDirector);
                            return (
                                <tr key={index} className={bgColor}>

                                    <td className="border border-black px-4 py-2">{elem.director} </td>

                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            ) : (
                <div>loading...</div>
            )}
        </>
    );

}

export default Example
