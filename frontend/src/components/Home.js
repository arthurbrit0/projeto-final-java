import React from 'react';
import './Home.css'
import {useNavigate} from "react-router-dom";

const Home = () => {

    return (
        <div className=''>
            <div className='mensagem'>
                <h1>Bem-vindo à Biblioteca Online</h1>
                <p>Use o menu para navegar pelas funcionalidades.</p>
            </div>
        </div>
    );
};

export default Home;
