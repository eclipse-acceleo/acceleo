/**
 * Generated with MTL UML 2 Java example
 */
package org.eclipse.acceleo.java;

// Start of user code for imports
import java.util.*;
// End of user code

/**
 * @author MTL
 */
public class Person {
    /**
     * the dateOfBirth attribute.
     */
    private Date dateOfBirth;
    /**
     * the name attribute.
     */
    private String name;
    /**
     * the emails attribute.
     */
    private List<String> emails;
    /**
     * the firstname attribute.
     */
    private String firstname;
    /**
     * the dateOfBirth getter.
     * @return the dateOfBirth.
     */
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    /**
     * the dateOfBirth setter.
     * @param p_dateOfBirth the dateOfBirth to set.
     */
    public void setDateOfBirth(Date p_dateOfBirth) {
        this.dateOfBirth = p_dateOfBirth;
    }
    /**
     * the name getter.
     * @return the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * the name setter.
     * @param p_name the name to set.
     */
    public void setName(String p_name) {
        this.name = p_name;
    }
    /**
     * the emails getter.
     * @return the emails.
     */
    public List<String> getEmails() {
        return this.emails;
    }

    /**
     * the emails setter.
     * @param p_emails the emails to set.
     */
    public void setEmails(List<String> p_emails) {
        this.emails = p_emails;
    }
    /**
     * the firstname getter.
     * @return the firstname.
     */
    public String getFirstname() {
        return this.firstname;
    }

    /**
     * the firstname setter.
     * @param p_firstname the firstname to set.
     */
    public void setFirstname(String p_firstname) {
        this.firstname = p_firstname;
    }
    /**
     * the owned attribute.
     */
    private List<Vehicle> owned;
    /**
     * the children attribute.
     */
    private List<Person> children;
    /**
     * the parent attribute.
     */
    private List<Person> parent;
    /**
     * the owner attribute.
     */
    private Person owner;
    /**
     *
     * @return
     */
    public String getFullName() {
        // Start of user code for operation getFullName
        // TODO should be implemented
        return null;
        // End of user code
    }
    /**
     * Calculate the age from birthdate to now.
     * @return
     */
    public Integer getAge() {
        // Start of user code for operation getAge
        // TODO should be implemented
        return null;
        // End of user code
    }
}
