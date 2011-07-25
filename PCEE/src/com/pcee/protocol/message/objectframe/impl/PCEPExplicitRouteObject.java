/**
 *  This file is part of Path Computation Element Emulator (PCEE).
 *
 *  PCEE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  PCEE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with PCEE.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.pcee.protocol.message.objectframe.impl;

import java.util.ArrayList;

import com.pcee.common.Address;
import com.pcee.protocol.message.PCEPConstantValues;
import com.pcee.protocol.message.objectframe.PCEPCommonObjectHeader;
import com.pcee.protocol.message.objectframe.PCEPObjectFrame;

/**
 * <pre>
 *  0                   1                   2                   3
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                                                               |
 * //                        (Subobjects)                          //
 * |                                                               |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * </pre>
 */

// TODO Generate methods to split and identify objects
public class PCEPExplicitRouteObject implements PCEPObjectFrame {

	private final String NAME = "Explicit Route Object";

	private PCEPCommonObjectHeader objectHeader;
	// private LinkedList<PCEPTLVObject> tlvList;
	private ArrayList<Address> traversedVertexList = new ArrayList<Address>();

	// private String subObjectsString;

	public PCEPExplicitRouteObject(PCEPCommonObjectHeader objectHeader, String binaryString) {
		this.setObjectHeader(objectHeader);
		this.setObjectBinaryString(binaryString);
		this.updateHeaderLength();
	}

	public PCEPExplicitRouteObject(PCEPCommonObjectHeader objectHeader, ArrayList<Address> traversedVertexList) {
		this.setObjectHeader(objectHeader);
		this.setTraversedVertexList(traversedVertexList);
		this.updateHeaderLength();
	}

	private void updateHeaderLength() {
		int objectFrameByteLength = this.getObjectFrameByteLength();
		this.getObjectHeader().setLengthDecimalValue(objectFrameByteLength);
	}

	/**
	 * Object
	 */
	public PCEPCommonObjectHeader getObjectHeader() {
		return objectHeader;
	}

	public void setObjectHeader(PCEPCommonObjectHeader objectHeader) {
		this.objectHeader = objectHeader;
	}

	public String getObjectBinaryString() {

		StringBuffer subObjectsStringBuffer = new StringBuffer();

		for (int i = 0; i < traversedVertexList.size(); i++) {
			Address address = traversedVertexList.get(i);
			subObjectsStringBuffer.append(address.getBinaryAddress());
		}

		return subObjectsStringBuffer.toString();
	}

	public void setObjectBinaryString(String binaryString) {

		ArrayList<Address> vertexList = new ArrayList<Address>();

		while (binaryString.length() > 0) {
			String addressBinaryString = binaryString.substring(0, 32);
			Address address = new Address(addressBinaryString);
			vertexList.add(address);

			binaryString = binaryString.substring(32);
		}

		setTraversedVertexList(vertexList);

	}

	public int getObjectFrameByteLength() {
		int objectsBinaryLength = this.getTraversedVertexListBinaryLength();
		int headerLength = PCEPConstantValues.COMMON_OBJECT_HEADER_LENGTH / 8;
		int objectFrameByteLength = objectsBinaryLength + headerLength;

		return objectFrameByteLength;
	}

	public String getObjectFrameBinaryString() {
		String headerBinaryString = this.getObjectHeader().getHeaderBinaryString();
		String objectBinaryString = this.getObjectBinaryString();

		return headerBinaryString + objectBinaryString;
	}

	public void setTraversedVertexList(ArrayList<Address> subObjects) {
		this.traversedVertexList = subObjects;
	}

	public ArrayList<Address> getTraversedVertexList() {
		return this.traversedVertexList;
	}

	private int getTraversedVertexListBinaryLength() {
		int length = 0;
		for (int i = 0; i < traversedVertexList.size(); i++) {
			length += traversedVertexList.get(i).getAddressByteLength();
		}
		return length;
	}

	public String getTraversedVertexes() {
		String traversedVertexesList = new String();

		for (Address address : traversedVertexList) {
			traversedVertexesList = traversedVertexesList + address.getAddress() + "-";
		}

		return traversedVertexesList;
	}

	// public String getTraversedVertexes() {
	// String traversedVertexesList = new String();
	//
	// for (int i = 0; i < traversedVertexList.size(); i++) {
	//
	// traversedVertexesList = traversedVertexesList +
	// traversedVertexList.get(i).getAddress() + "-->";
	//
	// if (i != traversedVertexList.size()) {
	// traversedVertexesList = traversedVertexesList + "--";
	// }
	// }
	//
	// return traversedVertexesList;
	// }

	public String toString() {

		String headerInfo = this.getObjectHeader().toString();

		StringBuffer objectInfo = new StringBuffer();

		objectInfo.append("<Include Route Object:");
		for (int i = 0; i < traversedVertexList.size(); i++) {
			objectInfo.append(traversedVertexList.get(i).toString());
		}
		objectInfo.append(">");

		return headerInfo + objectInfo;
	}

	public String binaryInformation() {

		String headerInfo = this.getObjectHeader().binaryInformation();

		StringBuffer objectInfo = new StringBuffer();

		for (int i = 0; i < traversedVertexList.size(); i++) {
			objectInfo.append(traversedVertexList.get(i).binaryInformation());
		}

		return headerInfo + objectInfo;
	}

	public String contentInformation() {
		String EROName = "[" + NAME;
		String subObjectsName = new String();

		for (Address address : traversedVertexList) {
			subObjectsName = subObjectsName + address.contentInformation();
		}

		return EROName + subObjectsName + "]";
	}

}