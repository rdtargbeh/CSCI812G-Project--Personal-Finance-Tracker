import { useState, useEffect } from "react";
import axiosInstance from "../service/axiosInstance";
import "../styles/profile.css";
import { useNavigate } from "react-router-dom";

const Profile = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    phoneNumber: "",
    address: "",
    email: "",
    profilePicture: "", // ✅ Ensure profile picture is included
    currency: "",
    timezone: "",
    preferredLanguage: "",
  });

  const [selectedFile, setSelectedFile] = useState(null);
  const [loading, setLoading] = useState(false);

  // ✅ Fetch user data when component loads
  useEffect(() => {
    fetchUserProfile();
  }, []);

  const fetchUserProfile = async () => {
    try {
      const response = await axiosInstance.get("/users/profile");
      console.log("✅ User Data:", response.data);
      setUser(response.data);
    } catch (error) {
      console.error(
        "❌ Error fetching user profile:",
        error.response?.data || error.message
      );
    }
  };

  // ✅ Handle Profile Update
  const handleUpdateProfile = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await axiosInstance.put("/users/profile", user);
      alert("✅ Profile updated successfully!");
      navigate("/dashboard"); // ✅ Redirect to dashboard after update
    } catch (error) {
      console.error(
        "❌ Error updating profile:",
        error.response?.data || error.message
      );
    } finally {
      setLoading(false);
    }
  };

  // ✅ Handle Profile Picture Upload
  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
  };

  const handleUpload = async () => {
    if (!selectedFile) return;
    const formData = new FormData();
    formData.append("file", selectedFile);

    try {
      const response = await axiosInstance.post(
        "/users/upload-profile-picture",
        formData,
        { headers: { "Content-Type": "multipart/form-data" } }
      );

      if (response.data.url) {
        console.log("✅ Profile Picture Uploaded:", response.data.url);

        // ✅ Update profile picture in state immediately
        setUser((prevUser) => ({
          ...prevUser,
          profilePicture: `${response.data.url}?t=${new Date().getTime()}`, // Prevent caching issues
        }));

        alert("✅ Profile picture updated successfully!");
        fetchUserProfile(); // ✅ Ensure latest user data is fetched
      }
    } catch (error) {
      console.error(
        "❌ Error uploading profile picture:",
        error.response?.data || error.message
      );
    }
  };

  return (
    <div className="profile-container">
      <h2>Edit Profile</h2>

      {/* ✅ Profile Picture Section */}
      <div className="profile-picture-section">
        <img
          src={user.profilePicture || "/img/default-profile.png"}
          alt="Profile"
          className="profile-img"
        />
        <input type="file" onChange={handleFileChange} />
        <button onClick={handleUpload} disabled={!selectedFile}>
          Upload
        </button>
      </div>

      <form onSubmit={handleUpdateProfile}>
        <input
          type="text"
          placeholder="First Name"
          value={user.firstName}
          onChange={(e) => setUser({ ...user, firstName: e.target.value })}
        />
        <input
          type="text"
          placeholder="Last Name"
          value={user.lastName}
          onChange={(e) => setUser({ ...user, lastName: e.target.value })}
        />
        <input
          type="text"
          placeholder="Phone Number"
          value={user.phoneNumber}
          onChange={(e) => setUser({ ...user, phoneNumber: e.target.value })}
        />
        <input
          type="text"
          placeholder="Address"
          value={user.address}
          onChange={(e) => setUser({ ...user, address: e.target.value })}
        />

        {/* ✅ Currency Selector */}
        <select
          value={user.currency}
          onChange={(e) => setUser({ ...user, currency: e.target.value })}
        >
          <option value="USD">USD ($)</option>
          <option value="EUR">EUR (€)</option>
          <option value="GBP">GBP (£)</option>
          <option value="LRD">LRD ($)</option>
        </select>

        {/* ✅ Timezone Selector */}
        <select
          value={user.timezone}
          onChange={(e) => setUser({ ...user, timezone: e.target.value })}
        >
          <option value="UTC">UTC</option>
          <option value="PST">PST</option>
          <option value="EST">EST</option>
          <option value="CST">CST</option>
        </select>

        {/* ✅ Preferred Language Selector */}
        <select
          value={user.preferredLanguage}
          onChange={(e) =>
            setUser({ ...user, preferredLanguage: e.target.value })
          }
        >
          <option value="en">English</option>
          <option value="fr">French</option>
          <option value="es">Spanish</option>
        </select>

        <input type="email" placeholder="Email" value={user.email} readOnly />

        <button type="submit" disabled={loading}>
          {loading ? "Updating..." : "Update Profile"}
        </button>
      </form>
    </div>
  );
};

export default Profile;

// import { useState, useEffect } from "react";
// import axiosInstance from "../service/axiosInstance";
// import "../styles/profile.css";
// import { Navigate, useNavigate } from "react-router-dom";

// const Profile = () => {
//   const navigate = useNavigate();
//   const [user, setUser] = useState({
//     firstName: "",
//     lastName: "",
//     phoneNumber: "",
//     address: "",
//     email: "",
//     profilePicture: "", // ✅ Include profile picture
//     currency: "",
//     timezone: "",
//     preferredLanguage: "",
//   });

//   const [selectedFile, setSelectedFile] = useState(null);

//   // ✅ Fetch user data when component loads
//   useEffect(() => {
//     fetchUserProfile();
//   }, []);

//   const fetchUserProfile = async () => {
//     try {
//       const response = await axiosInstance.get("/users/profile");
//       console.log("User Data:", response.data);
//       setUser(response.data);
//     } catch (error) {
//       console.error(
//         "❌ Error fetching user profile:",
//         error.response?.data || error.message
//       );
//     }
//   };

//   // ✅ Handle Profile Update
//   const handleUpdateProfile = async (e) => {
//     e.preventDefault();
//     try {
//       await axiosInstance.put("/users/profile", user);
//       alert("✅ Profile updated successfully!");
//       navigate("/dashboard"); // ✅ Redirect to dashboard after update
//     } catch (error) {
//       console.error(
//         "❌ Error updating profile:",
//         error.response?.data || error.message
//       );
//     }
//   };

//   // ✅ Handle Profile Picture Upload
//   const handleFileChange = (e) => {
//     setSelectedFile(e.target.files[0]);
//   };

//   const handleUpload = async () => {
//     if (!selectedFile) return;

//     const formData = new FormData();
//     formData.append("file", selectedFile);

//     try {
//       const response = await axiosInstance.post(
//         "/users/upload-profile-picture",
//         formData,
//         { headers: { "Content-Type": "multipart/form-data" } }
//       );

//       setUser((prevUser) => ({
//         ...prevUser,
//         profilePicture: response.data.url, // ✅ Update profile picture
//       }));

//       alert("✅ Profile picture updated successfully!");
//       fetchUserProfile(); // ✅ Ensure latest user data is fetched
//     } catch (error) {
//       console.error(
//         "❌ Error uploading profile picture:",
//         error.response?.data || error.message
//       );
//     }
//   };

//   return (
//     <div className="profile-container">
//       <h2>Edit Profile</h2>

//       {/* ✅ Profile Picture Section */}
//       <div className="profile-picture-section">
//         <img
//           src={user?.profilePicture || "/img/user-1.png"}
//           alt="Profile"
//           className="profile-img"
//         />
//         <input type="file" onChange={handleFileChange} />
//         <button onClick={handleUpload}>Upload</button>
//       </div>

//       <form onSubmit={handleUpdateProfile}>
//         <input
//           type="text"
//           placeholder="First Name"
//           value={user.firstName}
//           onChange={(e) => setUser({ ...user, firstName: e.target.value })}
//         />
//         <input
//           type="text"
//           placeholder="Last Name"
//           value={user.lastName}
//           onChange={(e) => setUser({ ...user, lastName: e.target.value })}
//         />
//         <input
//           type="text"
//           placeholder="Phone Number"
//           value={user.phoneNumber}
//           onChange={(e) => setUser({ ...user, phoneNumber: e.target.value })}
//         />
//         <input
//           type="text"
//           placeholder="Address"
//           value={user.address}
//           onChange={(e) => setUser({ ...user, address: e.target.value })}
//         />

//         <select
//           value={user.currency}
//           onChange={(e) => setUser({ ...user, currency: e.target.value })}
//         >
//           <option value="USD">USD ($)</option>
//           <option value="EUR">EUR (€)</option>
//           <option value="GBP">GBP (£)</option>
//           <option value="GBP">LRD ($)</option>
//         </select>

//         <select
//           value={user.timezone}
//           onChange={(e) => setUser({ ...user, timezone: e.target.value })}
//         >
//           <option value="UTC">UTC</option>
//           <option value="PST">PST</option>
//           <option value="EST">EST</option>
//           <option value="EST">CST</option>
//         </select>

//         <select
//           value={user.preferredLanguage}
//           onChange={(e) =>
//             setUser({ ...user, preferredLanguage: e.target.value })
//           }
//         >
//           <option value="en">English</option>
//           <option value="fr">French</option>
//           <option value="es">Spanish</option>
//         </select>

//         <input type="email" placeholder="Email" value={user.email} readOnly />

//         <button type="submit">Update Profile</button>
//       </form>
//     </div>
//   );
// };

// export default Profile;
