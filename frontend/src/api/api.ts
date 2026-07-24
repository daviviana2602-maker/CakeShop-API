import axios from "axios"


const api = axios.create({
  baseURL: "http://localhost:8080/v1",
  withCredentials: true     
})



api.interceptors.request.use((config) => {
  const token = localStorage.getItem("accessToken")


  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }

  return config
})


api.interceptors.response.use(
  (response) => response,

  async (error) => {
    const originalRequest = error.config

    if (error.response?.status === 401 && !originalRequest._retry && !originalRequest.url.includes("/auth/refresh")) {
      originalRequest._retry = true

      try {
        

        delete originalRequest.headers.Authorization  // repeat the request without an invalid access token
        const response = await api.post("/auth/refresh")

        
        const newAccessToken = response.data
        

        localStorage.setItem("accessToken", newAccessToken)

        originalRequest.headers.Authorization = `Bearer ${newAccessToken}`


        return api(originalRequest)


      } catch (refreshError) {

        localStorage.removeItem("accessToken")

        return Promise.reject(refreshError)
      }
    }

    return Promise.reject(error)

  }
)


export default api