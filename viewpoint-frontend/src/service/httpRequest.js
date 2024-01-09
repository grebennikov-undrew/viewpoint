import axios from 'axios'

// import { redirect, useNavigate } from "react-router-dom";
// const history= useNavigate();

const BASE_URL = '/api'

const instance = axios.create({
    baseURL: BASE_URL,
    validateStatus: function (status) {
        return status < 600;
    },
});

instance.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers['Authorization'] = 'Bearer ' + token;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

instance.interceptors.response.use(response => {
    const token = localStorage.getItem('token');
    if (response.status === 401 || !token) {
        localStorage.removeItem('token');
        window.location.replace("/login");
    }

    return response;
 }, error => {
    const token = localStorage.getItem('token');
    if (error.response.status === 401 || !token) {
        localStorage.removeItem('token');
        window.location.replace("/login");
    }
 });

export const httpRequest = instance;