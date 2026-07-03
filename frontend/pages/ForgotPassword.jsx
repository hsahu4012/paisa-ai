import { useState } from "react";
import axios from "axios";

const ForgotPassword = () => {
  const [email, setEmail] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/auth/forgot-password",
        { email }
      );

      sessionStorage.setItem("email", email);

      sessionStorage.setItem(
        "securityQuestions",
        JSON.stringify(response.data.data)
      );

      window.location.href = "/verify-security-answers";
    } catch (error) {
      alert(error.response?.data?.message || "Something went wrong");
    }
  };

  return (
    <div className="min-h-screen flex justify-center items-center bg-indigo-100">
      <div className="bg-white p-8 rounded-xl w-96 shadow-lg">
        <h2 className="text-2xl font-bold mb-6">
          Forgot Password
        </h2>

        <form onSubmit={handleSubmit}>
          <input
            type="email"
            placeholder="Enter Email"
            className="w-full border p-3 rounded-lg mb-4"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <button
            className="w-full bg-indigo-600 text-white p-3 rounded-lg"
            type="submit"
          >
            Continue
          </button>
        </form>
      </div>
    </div>
  );
};

export default ForgotPassword;