import React from 'react';
import { useNavigate } from 'react-router-dom';
import NavigationBar from './NavigationBar'; // 경로 확인 필요

function ContainerRequestForm() {
  const navigate = useNavigate();
   const tableData = [
      ['', 'Image 1', 'Image 2', 'Image 3'],
      ['Cuda Version', '11.2', '11.8', '-'],
      ['Tensorflow Version', '2.8.2', '2.12.0', '-'],
      ['OS', 'Ubuntu 20.04', 'Ubuntu 20.04', 'Ubuntu 20.04']
    ];

  return (
    <div style={styles.container}>
      <NavigationBar />
      <h1 style={styles.heading}>Container Request Form</h1>
      <form style={styles.form}>
        <div style={styles.formGroup}>
          <p style={styles.label}>Choose a Container Image</p>
          <div style={styles.radioGroup}>
            <label style={styles.radioLabel}>
              <input type="radio" name="image" value="image1" required style={styles.radioButton} />
              Image 1
            </label>
             <label style={styles.radioLabel}>
              <input type="radio" name="image" value="image2" required style={styles.radioButton} />
              Image 2
            </label>
             <label style={styles.radioLabel}>
              <input type="radio" name="image" value="image3" required style={styles.radioButton} />
              Image 3
            </label>
            {/* Repeat for other images */}
          </div>
        </div>

        <div style={styles.tableContainer}>
          <table style={styles.table}>
            <tbody>
              {tableData.map((row, rowIndex) => (
                <tr key={rowIndex}>
                  {row.map((cell, colIndex) => (
                    <td key={colIndex} style={styles.tableCell}>{cell}</td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div style={styles.formGroup}>
          <p style={styles.label}>Environment</p>
          <div style={styles.radioGroup}>
            <label style={styles.radioLabel}>
              <input type="radio" name="environment" value="LAB" required style={styles.radioButton} />
              LAB
            </label>
            <label style={styles.radioLabel}>
              <input type="radio" name="environment" value="FARM" style={styles.radioButton} />
              FARM
            </label>
          </div>
        </div>

        {/* User information inputs */}
        <div style={styles.inputGroup}>
          <input type="text" placeholder="Student ID" required style={styles.inputField} />
          <input type="text" placeholder="Department" required style={styles.inputField} />
          <input type="text" placeholder="Professor Name" required style={styles.inputField} />

           <textarea placeholder="Usage" required style={{...styles.inputField, height: '100px'}} />
        </div>

        {/* GPU Model selection */}
        <div style={styles.formGroup}>
          <p style={styles.label}>Choose a GPU Model</p>
          <div style={styles.radioGroup}>
            <label style={styles.radioLabel}>
              <input type="radio" name="gpu" value="A100" required style={styles.radioButton} />
              RTX 3090
            </label>
            {/* Repeat for other GPU models */}
          </div>
        </div>
        <p style={styles.cautionText}>여기에 유의사항 적기.</p>

        <button type="submit" style={styles.submitButton}>Submit</button>
      </form>
    </div>
  );
}

// Define your styles here
const styles = {
  container: {
    padding: '20px',
    maxWidth: '600px',
    margin: 'auto',
  },
  heading: {
    textAlign: 'center',
    marginBottom: '20px',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
  },
  formGroup: {
    marginBottom: '20px',
  },
  label: {
    marginBottom: '10px',
    fontWeight: 'bold',
  },
  radioGroup: {
    display: 'flex',
    flexDirection: 'column',
  },
  radioLabel: {
    marginBottom: '5px',
  },
  radioButton: {
    marginRight: '10px',
  },
  inputGroup: {
    marginBottom: '20px',
  },
  inputField: {
      padding: '10px',
      marginBottom: '10px',
      borderRadius: '5px',
      border: '1px solid #ccc',
      outline: 'none',
      resize: 'none' // Disable resizing for textarea
    },
    submitButton: {/* Submit button styles */},
    tableContainer: {
      overflowX: 'auto',
      marginBottom: '20px'
    },
    table: {
      width: '100%',
      borderCollapse: 'collapse',
    },
    tableCell: {
      border: '1px solid #ccc',
      textAlign: 'center',
      padding: '8px'
    },
    cautionText: {
      color: 'red',
      fontWeight: 'bold',
      marginBottom: '20px',
      textAlign: 'center'
    },
  };

  export default ContainerRequestForm;