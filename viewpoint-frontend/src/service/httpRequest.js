import axios from 'axios'

// import { redirect, useNavigate } from "react-router-dom";
// const history= useNavigate();

const BASE_URL = 'http://localhost:8080/api'

const instance = axios.create({
    withCredentials: true,
    baseURL: BASE_URL
});

instance.interceptors.response.use(response => {
    return response;
 }, error => {
    if (error.response.status === 401) window.location.replace("/login");
 });

export const httpRequest = instance;