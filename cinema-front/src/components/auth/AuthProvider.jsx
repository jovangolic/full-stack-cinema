import React, { createContext, useState, useContext } from "react"
import jwt_decode from "jwt-decode"
import { useEffect } from 'react';

export const AuthContext = createContext({
	user: null,
	handleLogin: (token) => {},
	handleLogout: () => {}
})

export const AuthProvider = ({ children }) => {
	const [user, setUser] = useState(null)

	useEffect(() => {
		const token = localStorage.getItem("token")
		if (token) {
			try {
				const decodedUser = jwt_decode(token)
				setUser(decodedUser)
			} catch (error) {
				console.error("Nevalidan token:", error)
				handleLogout() // očisti localStorage ako je token nevalidan
			}
		}
	}, [])

	const handleLogin = (token) => {
		const decodedUser = jwt_decode(token)
		localStorage.setItem("userId", decodedUser.sub)
		localStorage.setItem("userRole", decodedUser.roles)
		localStorage.setItem("token", token)
		setUser(decodedUser)
	}

	const handleLogout = () => {
		localStorage.removeItem("userId")
		localStorage.removeItem("userRole")
		localStorage.removeItem("token")
		setUser(null)
	}

	return (
		<AuthContext.Provider value={{ user, handleLogin, handleLogout }}>
			{children}
		</AuthContext.Provider>
	)
}

export const useAuth = () => {
	return useContext(AuthContext)
}