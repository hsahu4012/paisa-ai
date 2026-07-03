import { useState } from "react";
import axios from "axios";

const ResetPassword = () => {
  const resetToken =
    sessionStorage.getItem("resetToken");

  const [formData, setFormData] = useState({
    newPassword: "",
    confirmPassword: "",
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/auth/reset-password",
        {
          resetToken,
          newPassword: formData.newPassword,
          confirmPassword: formData.confirmPassword,
        }
      );

      alert(response.data.message);

      sessionStorage.clear();

      window.location.href = "/login";
    } catch (error) {
      alert(
        error.response?.data?.message ||
          "Failed to reset password"
      );
    }
  };

  return (
    <div className="min-h-screen flex justify-center items-center bg-indigo-100">
      <div className="bg-white p-8 rounded-xl shadow-lg w-96">
        <h2 className="text-2xl font-bold mb-6">
          Reset Password
        </h2>

        <form onSubmit={handleSubmit}>
          <input
            type="password"
            name="newPassword"
            placeholder="New Password"
            className="w-full border p-3 rounded-lg mb-4"
            value={formData.newPassword}
            onChange={handleChange}
            required
          />

          <input
            type="password"
            name="confirmPassword"
            placeholder="Confirm Password"
            className="w-full border p-3 rounded-lg mb-4"
            value={formData.confirmPassword}
            onChange={handleChange}
            required
          />

          <button
            type="submit"
            className="w-full bg-indigo-600 text-white p-3 rounded-lg"
          >
            Reset Password
          </button>
        </form>
      </div>
    </div>
  );
};

export default ResetPassword;