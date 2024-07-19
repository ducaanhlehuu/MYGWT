package com.hello.client.activities.class_divide;

import java.util.List;

public class DivisionResult {
    List<StudentClass> classes;
    List<StudentClass> groups;
    private int groupQty;

    public List<StudentClass> getGroups() {
        return groups;
    }

    public void setGroups(List<StudentClass> groups) {
        this.groups = groups;
    }

    public List<StudentClass> getClasses() {
        return classes;
    }

    public void setClasses(List<StudentClass> classes) {
        this.classes = classes;
    }

    public int getGroupQty() {
        return groupQty;
    }

    public void setGroupQty(int groupQty) {
        this.groupQty = groupQty;
    }

    public DivisionResult(List<StudentClass> classes, List<StudentClass> groups, int groupQty) {
        super();
        this.classes = classes;
        this.groups = groups;
        this.groupQty = groupQty;
    }

	@Override
	public String toString() {
		return "DivisionResult [classes=" + classes + ", groups=" + groups + ", groupQty=" + groupQty + "]";
	}

    
    
}